package service;

import model.User;

public interface AccountService {
    // ✅ 新增存款方法
    boolean deposit(User user, double amount);
}
