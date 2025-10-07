package dao.impl;

import dao.AccountDao;
import util.DbConnection;

import java.sql.*;

/**
 * AccountDao 實作 - 對應資料表 member 的 balance 欄位
 */
public class AccountDaoImpl implements AccountDao {

    @Override
    public double getBalance(int userId) {
        double balance = 0;
        String sql = "SELECT balance FROM member WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble("balance");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    @Override
	    public boolean updateBalance(int userId, double newBalance) {
	        String sql = "UPDATE member SET balance = ? WHERE id = ?";
	        try (Connection conn = DbConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setDouble(1, newBalance);
	            ps.setInt(2, userId);
	
	            int rows = ps.executeUpdate();
	            return rows > 0;
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	@Override
	public int findUserIdByUsername(String username) {
	    String sql = "SELECT id FROM member WHERE username = ?";
	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // ❌ 沒找到回傳 -1
	}
}