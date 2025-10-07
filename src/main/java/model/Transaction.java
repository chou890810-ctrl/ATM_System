package model;

import java.time.LocalDateTime;

/**
 * 交易紀錄資料模型
 */
public class Transaction {
    private int id;
    private String type; // withdraw / deposit / transfer
    private double amount;
    private LocalDateTime time;
    private int userId;

    

    public Transaction(int id, String type, double amount, LocalDateTime time, int userId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
