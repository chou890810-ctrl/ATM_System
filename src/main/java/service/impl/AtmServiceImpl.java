package service.impl;

import dao.AccountDao;
import dao.TransactionDao;
import dao.impl.AccountDaoImpl;
import dao.impl.TransactionDaoImpl;
import exception.InsufficientBalanceException;
import model.TransactionRecord;
import model.User;
import service.AtmService;

import java.time.LocalDateTime;

public class AtmServiceImpl implements AtmService {
    private AccountDao accountDao = new AccountDaoImpl();
    private TransactionDao transactionDao = new TransactionDaoImpl();

    @Override
    public double checkBalance(int userId) {
        return accountDao.getBalance(userId);
    }

    @Override
    public boolean withdraw(User user, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("提款金額必須大於 0");
        }

        double currentBalance = accountDao.getBalance(user.getId());

        if (currentBalance < amount) {
            throw new InsufficientBalanceException("餘額不足，提款失敗");
        }

        double newBalance = currentBalance - amount;
        boolean updated = accountDao.updateBalance(user.getId(), newBalance);

        if (updated) {
            user.setBalance(newBalance);
            TransactionRecord tx = new TransactionRecord(
                user.getId(),
                "提款",
                amount,
                LocalDateTime.now()
            );
            transactionDao.insert(tx);
            return true;
        }
        return false;
    }

    // ✅ 新增：轉帳功能
    public boolean transfer(User fromUser, String toUsername, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("轉帳金額必須大於 0");
        }

        // 查詢收款人 ID
        int toUserId = accountDao.findUserIdByUsername(toUsername);
        if (toUserId == -1) {
            throw new IllegalArgumentException("❌ 找不到收款帳號");
        }

        // 確認轉出者餘額
        double fromBalance = accountDao.getBalance(fromUser.getId());
        if (fromBalance < amount) {
            throw new InsufficientBalanceException("❌ 餘額不足，轉帳失敗");
        }

        // 扣款與加款
        double newFromBalance = fromBalance - amount;
        double toBalance = accountDao.getBalance(toUserId);
        double newToBalance = toBalance + amount;

        boolean fromUpdated = accountDao.updateBalance(fromUser.getId(), newFromBalance);
        boolean toUpdated = accountDao.updateBalance(toUserId, newToBalance);

        if (fromUpdated && toUpdated) {
            fromUser.setBalance(newFromBalance);

            // ✏️ 寫入交易紀錄
            TransactionRecord outTx = new TransactionRecord(
                fromUser.getId(), "轉出", amount, LocalDateTime.now()
            );
            TransactionRecord inTx = new TransactionRecord(
                toUserId, "轉入", amount, LocalDateTime.now()
            );

            transactionDao.insert(outTx);
            transactionDao.insert(inTx);

            return true;
        }
        return false;
    }
}



