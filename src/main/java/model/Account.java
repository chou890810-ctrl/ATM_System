package model;

/**
 * 帳戶資料模型 - 與 User 分開主要是為了未來擴充（例如一人多帳戶）
 */
public class Account {
    private int id;
    private String accountNumber;
    private double balance;

    public Account() {}

    public Account(int id, String accountNumber, double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}