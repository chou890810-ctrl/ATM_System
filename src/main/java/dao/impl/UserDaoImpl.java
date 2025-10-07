package dao.impl;

import dao.UserDao;
import model.User;
import util.DbConnection;

import java.sql.*;

/**
 * UserDao 實作類別 - 透過 JDBC 連線 MySQL
 */
public class UserDaoImpl implements UserDao {
	

    @Override
    public User findByUsername(String username) {
    	
        User user = null;
        String sql = "SELECT * FROM member WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getDouble("balance")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

	@Override
	public boolean insert(User user) {
		 String sql = "INSERT INTO member (username, password, name, balance) VALUES (?, ?, ?, ?)";
		    try (Connection conn = DbConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setString(1, user.getUsername());
		        ps.setString(2, user.getPassword());
		        ps.setString(3, user.getName());
		        ps.setDouble(4, user.getBalance());
		        return ps.executeUpdate() > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
	}

	@Override
	public boolean updatePassword(int userId, String newPassword) {
		String sql = "UPDATE member SET password=? WHERE id=?";
	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, newPassword);
	        ps.setInt(2, userId);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean deleteUser(int userId) {
		String sql = "DELETE FROM member WHERE id=?";
	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	
}	
