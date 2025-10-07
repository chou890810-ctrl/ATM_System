package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.LoginService;

/**
 * 登入服務實作
 */
public class LoginServiceImpl implements LoginService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // 登入成功
        }
        return null; // 登入失敗
    }
}