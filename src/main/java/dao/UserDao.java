package dao;

import model.User;

/**
 * 使用者 DAO - 負責登入驗證與會員查詢
 */
public interface UserDao {
    /**
     * 根據帳號查詢使用者資料
     * @param username 使用者帳號
     * @return User 物件（若找不到則回傳 null）
     */
    User findByUsername(String username);
    boolean insert(User user);
    boolean updatePassword(int userId, String newPassword);  // ✅ 修改密碼
    boolean deleteUser(int userId);                          // ✅ 刪除帳號
}