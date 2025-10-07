package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User u = userDao.findByUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return false; // 帳號重複
        }
        return userDao.insert(user);
    }

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean updatePassword(int userId, String newPassword) {
	    return userDao.updatePassword(userId, newPassword);
	}

	@Override
	public boolean deleteUser(int userId) {
	    return userDao.deleteUser(userId);
	}
}