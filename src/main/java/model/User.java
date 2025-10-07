package model;

/**
 * 使用者資料模型 - 對應資料表 member
 */
public class User {
    private int id;             // 使用者ID
    private String username;    // 帳號
    private String password;    // 密碼
    private String name;        // 使用者姓名
    private double balance;     // 餘額

    public User() {}

    public User(int id, String username, String password, String name, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.balance = balance;
    }

    // Getter / Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
