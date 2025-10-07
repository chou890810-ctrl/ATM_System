package service;

import model.User;

/**
 * 登入服務介面 - 負責帳號密碼驗證
 */
public interface LoginService {
    /**
     * 根據帳號密碼驗證使用者
     * @param username 帳號
     * @param password 密碼
     * @return User 物件（登入成功），若失敗回傳 null
     */
    User login(String username, String password);
}