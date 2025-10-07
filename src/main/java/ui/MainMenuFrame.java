package ui;

import model.TransactionRecord;
import model.User;
import service.AtmService;
import service.impl.AtmServiceImpl;
import service.impl.UserServiceImpl;
import service.AccountService;
import service.impl.AccountServiceImpl;

import javax.swing.*;
import dao.impl.TransactionDaoImpl;
import exception.InsufficientBalanceException;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainMenuFrame extends JFrame {

    private JLabel lblTime;           // ✅ 唯一的時間標籤
    private JLabel lblBalance;
    private User currentUser;
    private AtmService atmService = new AtmServiceImpl();
    private AccountService accountService;
    private Double lastWithdrawAmount = null;  // 記錄最後一次提款金額（可能為 null）

    public MainMenuFrame(User user) {
        this.currentUser = user;
        this.accountService = new AccountServiceImpl();

        setTitle("ATM 主選單");
        setBounds(100, 100, 557, 508);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ✅ 歡迎文字
        JLabel lblWelcome = new JLabel("歡迎使用，" + user.getName());
        lblWelcome.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblWelcome.setBounds(30, 20, 300, 30);
        contentPane.add(lblWelcome);

        // ✅ 時間標籤
        lblTime = new JLabel();
        lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTime.setBounds(250, 20, 220, 30);
        contentPane.add(lblTime);

        // ✅ 每秒更新時間
        new Timer(1000, e -> {
            String now = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            lblTime.setText("現在時間：" + now);
        }).start();

        // ✅ 提款按鈕
        JButton btnWithdraw = new JButton("提款");
        btnWithdraw.setBounds(291, 180, 150, 40);
        contentPane.add(btnWithdraw);

        // ✅ 存款按鈕
        JButton btnDeposit = new JButton("💰 存款");
        btnDeposit.setBounds(80, 180, 150, 40);
        contentPane.add(btnDeposit);
        
        // ✅ 轉帳按鈕
        JButton btnTransfer = new JButton("💸 轉帳");
        btnTransfer.setBounds(291, 250, 150, 40);
        contentPane.add(btnTransfer);

        // ✅ 登出按鈕
        JButton btnLogout = new JButton("登出");
        btnLogout.setBounds(80, 250, 150, 40);
        contentPane.add(btnLogout);

        // ✅ 顯示餘額
        lblBalance = new JLabel("目前餘額：");
        lblBalance.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
        lblBalance.setBounds(80, 310, 300, 30);
        contentPane.add(lblBalance);

        // ✅ 修改密碼按鈕
        JButton btnChangePwd = new JButton("修改密碼");
        btnChangePwd.setBounds(80, 373, 150, 40);
        contentPane.add(btnChangePwd);

        // ✅ 刪除帳號按鈕
        JButton btnDelete = new JButton("刪除帳號");
        btnDelete.setBounds(291, 373, 150, 40);
        contentPane.add(btnDelete);

        // ✅ 查看交易紀錄按鈕
        JButton btnLog = new JButton("📜 查看交易紀錄");
        btnLog.setBounds(180, 423, 150, 40);
        contentPane.add(btnLog);

        // 📜 查看交易紀錄事件
        btnLog.addActionListener(e -> {
            new TransactionFrame(currentUser.getId()).setVisible(true);
        });

        // ✅ 提款事件
        btnWithdraw.addActionListener(e -> {
        	String input = JOptionPane.showInputDialog("請輸入提款金額：");
            if (input != null && !input.isBlank()) {
                try {
                    double amount = Double.parseDouble(input);
                    boolean success = atmService.withdraw(currentUser, amount);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "✅ 提款成功！已領出：" + amount + " 元");
                        lastWithdrawAmount = amount; // 記住本次提款金額
                        new ChangeDialog(MainMenuFrame.this, true, amount).setVisible(true); // 直接帶入找零
                        updateBalanceLabel();
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ 餘額不足，提款失敗");
                    }
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "❌ 金額格式錯誤！");
                }
                catch (InsufficientBalanceException ex) {
                    JOptionPane.showMessageDialog(null, "❌ " + ex.getMessage());}
                catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "❌ " + ex.getMessage());}
            }
        });
        
     // ✅ 轉帳事件
        btnTransfer.addActionListener(e -> {
            String toUsername = JOptionPane.showInputDialog("請輸入收款人帳號：");
            if (toUsername == null) {
                return;
            }
            if (toUsername == null || toUsername.isBlank()) {
                JOptionPane.showMessageDialog(null, "❌ 帳號不能為空！");
                return;
            }

            String inputAmount = JOptionPane.showInputDialog("請輸入轉帳金額：");
            if (inputAmount == null || inputAmount.isBlank()) {
                JOptionPane.showMessageDialog(null, "❌ 金額不能為空！");
                return ;
            }

            try {
                double amount = Double.parseDouble(inputAmount);

                boolean success = atmService.transfer(currentUser, toUsername, amount);
                if (success) {
                    JOptionPane.showMessageDialog(
                        null,
                        "✅ 轉帳成功！\n已轉出：" + amount + " 元\n收款人帳號：" + toUsername,
                        "轉帳成功",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    // 📊 轉帳後即時刷新餘額
                    double balance = atmService.checkBalance(currentUser.getId());
                    lblBalance.setText("目前餘額：" + balance + " 元");

                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "❌ 轉帳失敗，請確認帳號與餘額！",
                        "轉帳失敗",
                        JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "❌ 金額格式錯誤，請輸入數字！");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ " + ex.getMessage());
            }
        });
        

        // ✅ 登出事件
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        // ✅ 存款事件
        btnDeposit.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("請輸入要存入的金額：");
            if (input != null && !input.isBlank()) {
                try {
                    double amount = Double.parseDouble(input);
                    boolean success = accountService.deposit(currentUser, amount);
                    if (success) {
                        // ⬅️ 修正這裡：加入 LocalDateTime.now()
                        TransactionRecord record = new TransactionRecord(
                                currentUser.getId(),
                                "存入",
                                amount,
                                LocalDateTime.now()
                        );
                        new TransactionDaoImpl().insert(record);
                        JOptionPane.showMessageDialog(null, "✅ 存款成功！目前餘額：" + currentUser.getBalance());
                        updateBalanceLabel();
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ 存款失敗，請輸入大於 0 的金額");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "❌ 金額格式錯誤！");
                }
            }
        });

        // ✅ 修改密碼事件
        btnChangePwd.addActionListener(e -> {
            String newPwd = JOptionPane.showInputDialog("請輸入新密碼：");
            if (newPwd != null && !newPwd.isBlank()) {
                boolean success = new UserServiceImpl().updatePassword(currentUser.getId(), newPwd);
                if (success) {
                    JOptionPane.showMessageDialog(null, "✅ 密碼修改成功，請重新登入");
                    new LoginFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "❌ 修改失敗");
                }
            }
        });

        // ✅ 刪除帳號事件
        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "⚠️ 確定要刪除帳號嗎？這會永久刪除資料！", "警告", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = new UserServiceImpl().deleteUser(currentUser.getId());
                if (success) {
                    JOptionPane.showMessageDialog(null, "✅ 帳號已刪除，再見！");
                    new LoginFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "❌ 刪除失敗");
                }
            }
        });
        updateBalanceLabel();
    }
    private void updateBalanceLabel() {
        double balance = atmService.checkBalance(currentUser.getId());
        lblBalance.setText("目前餘額：" + balance + " 元");
    }
    // ✅ 主程式入口（測試用）
    public static void main(String[] args) {
        User testUser = new User(1, "test", "1234", "王小明", 5000);
        new MainMenuFrame(testUser).setVisible(true);
    }	
    
    
}
