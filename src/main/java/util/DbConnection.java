package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 📡 DbConnection - 負責建立與 MySQL 的連線
 * 所有 DAO 層都會呼叫這個類別來存取資料庫
 */
public class DbConnection {

    // 修改成你的資料庫資訊
    private static final String URL = "jdbc:mysql://localhost:3306/ATM_system?serverTimezone=Asia/Taipei&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    /**
     * 建立資料庫連線
     * @return Connection 連線物件
     * @throws SQLException 若連線失敗
     */
    public static Connection getConnection() throws SQLException {
        try {
            // 載入 MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 回傳資料庫連線
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
