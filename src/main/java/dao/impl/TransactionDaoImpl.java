package dao.impl;

import dao.TransactionDao;
import model.TransactionRecord;
import util.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    private Connection conn;

    public TransactionDaoImpl() {
        try {
			conn = DbConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * ✅ 新增交易紀錄
     */
    @Override
    public boolean insert(TransactionRecord tx) {
        String sql = "INSERT INTO transaction_record (user_id, type, amount, time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tx.getUserId());
            ps.setString(2, tx.getType());
            ps.setDouble(3, tx.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(tx.getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ✅ 查詢某使用者的所有交易紀錄（按時間倒序）
     */
    @Override
    public List<TransactionRecord> findByUserId(int userId) {
        List<TransactionRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM transaction_record WHERE user_id = ? ORDER BY time DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionRecord tx = new TransactionRecord(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("time").toLocalDateTime()
                );
                list.add(tx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * ✅ 清空指定使用者的交易紀錄
     */
    public boolean clearTransactionsByUserId(int userId) {
        String sql = "DELETE FROM transaction_record WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int rows = ps.executeUpdate();
            return rows > 0; // 回傳是否有刪除資料

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 🔍 條件查詢交易紀錄（可選：類型 / 日期 / 金額範圍）
     */
    public List<TransactionRecord> searchTransactions(
            int userId,
            String type,
            LocalDate startDate,
            LocalDate endDate,
            Double minAmount,
            Double maxAmount) {

        List<TransactionRecord> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM transaction_record WHERE user_id = ?");
        if (type != null && !type.equals("all")) {
            sql.append(" AND type = ?");
        }
        if (startDate != null) {
            sql.append(" AND time >= ?");
        }
        if (endDate != null) {
            sql.append(" AND time <= ?");
        }
        if (minAmount != null) {
            sql.append(" AND amount >= ?");
        }
        if (maxAmount != null) {
            sql.append(" AND amount <= ?");
        }
        sql.append(" ORDER BY time DESC");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setInt(index++, userId);

            if (type != null && !type.equals("all")) {
                ps.setString(index++, type);
            }
            if (startDate != null) {
                ps.setTimestamp(index++, Timestamp.valueOf(startDate.atStartOfDay()));
            }
            if (endDate != null) {
                // 包含結束日期整天（+1 天）
                ps.setTimestamp(index++, Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));
            }
            if (minAmount != null) {
                ps.setDouble(index++, minAmount);
            }
            if (maxAmount != null) {
                ps.setDouble(index++, maxAmount);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TransactionRecord tx = new TransactionRecord(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("time").toLocalDateTime()
                );
                list.add(tx);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
