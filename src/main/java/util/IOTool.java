package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 📦 IOTool - 處理所有輸入/輸出功能的工具類
 * 功能：
 * 1️⃣ 記錄交易（提款 / 存款 / 刪除帳號等）
 * 2️⃣ 讀取交易紀錄
 * 3️⃣ 匯出交易紀錄檔案
 */
public class IOTool {

    private static final String LOG_FILE = "transaction_log.txt";

    /**
     * ✅ 記錄交易
     * @param username 使用者名稱
     * @param action   動作（提款、存款、刪除帳號...）
     * @param amount   金額（如果沒有可傳 0）
     */
    public static void logTransaction(String username, String action, double amount) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String record = String.format("%s | 使用者：%s | 動作：%s | 金額：%.2f 元%n", time, username, action, amount);
            bw.write(record);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ✅ 讀取所有交易紀錄（回傳字串，可顯示在 JTextArea 或 JOptionPane 中）
     */
    public static String readTransactionLog() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            return "⚠️ 尚無交易紀錄！";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * ✅ 清除交易紀錄（可選功能）
     */
    public static void clearLog() {
        try (FileWriter fw = new FileWriter(LOG_FILE, false)) {
            fw.write(""); // 清空檔案
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
