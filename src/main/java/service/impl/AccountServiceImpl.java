package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import model.User;
import service.AccountService;

public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public boolean deposit(User user, double amount) {
        // ✅ 1. 檢查金額是否合法
        if (amount <= 0) return false;

        // ✅ 2. 從資料庫取得「最新餘額」
        double currentBalance = accountDao.getBalance(user.getId());

        // ✅ 3. 計算新餘額
        double newBalance = currentBalance + amount;

        // ✅ 4. 更新資料庫
        boolean result = accountDao.updateBalance(user.getId(), newBalance);

        // ✅ 5. 同步回記憶體中的 user 物件（確保 UI 顯示正確）
        if (result) {
            user.setBalance(newBalance);
        }

        return result;
    }
}