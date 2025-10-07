package service;

import model.User;

public interface UserService {
    User login(String username, String password); // 登入
    boolean register(User user);                  // ✅ 註冊
	User findByUsername(String username);
	boolean updatePassword(int userId, String newPassword);
	boolean deleteUser(int userId);
}