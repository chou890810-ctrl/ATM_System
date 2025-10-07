package model;

import java.time.LocalDateTime;

public class TransactionRecord {
    private int id;             // 交易紀錄編號
    private int userId;         // 對應的使用者 ID
    private String type;        // "deposit" 或 "withdraw"
    
    private double amount;      // 金額
    private LocalDateTime time; // 交易時間

    public TransactionRecord() {}
    
    
    public TransactionRecord(int id, int userId, String type, double amount, LocalDateTime time) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    // ✅ 方便插入新交易用（不含 id）
    public TransactionRecord(int userId, String type, double amount, LocalDateTime time) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
}
