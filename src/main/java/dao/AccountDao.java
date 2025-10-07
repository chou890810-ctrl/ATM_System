package dao;

import model.User;

/**
 * 帳戶 DAO - 處理餘額查詢、提款、更新等功能
 */
public interface AccountDao {
    /**
     * 查詢帳戶餘額
     * @param userId 使用者 ID
     * @return 餘額
     */
	int findUserIdByUsername(String username);
    double getBalance(int userId);

    /**
     * 更新帳戶餘額
     * @param userId 使用者 ID
     * @param newBalance 新餘額
     * @return 是否更新成功
     */
    boolean updateBalance(int userId, double newBalance);
}