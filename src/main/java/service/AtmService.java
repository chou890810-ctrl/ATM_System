package service;

import model.User;

/**
 * ATM 主要功能服務介面
 */
public interface AtmService {
    /**
     * 查詢餘額
     * @param userId 使用者 ID
     * @return 餘額
     */
    double checkBalance(int userId);
    
    /**
     * 提款功能
     * @param currentUser 使用者 ID
     * @param amount 提款金額
     * @return 提款是否成功
     */
    boolean withdraw(User user, double amount);

	boolean transfer(User currentUser, String toUsername, double amount);
}