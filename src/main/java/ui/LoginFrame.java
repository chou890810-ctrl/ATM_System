package ui;

import javax.swing.*;
import model.User;
import service.LoginService;
import service.impl.LoginServiceImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.*;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private UserService userService = new UserServiceImpl();

    public LoginFrame() {
        setTitle("ATM 系統 - 登入");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("🔑 登入系統");
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblTitle.setBounds(120, 20, 150, 30);
        add(lblTitle);

        JLabel lblUsername = new JLabel("帳號：");
        lblUsername.setBounds(50, 70, 80, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(130, 70, 150, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("密碼：");
        lblPassword.setBounds(50, 110, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(130, 110, 150, 25);
        add(txtPassword);

        JButton btnLogin = new JButton("登入");
        btnLogin.setBounds(60, 160, 100, 30);
        add(btnLogin);

        JButton btnRegister = new JButton("註冊");
        btnRegister.setBounds(180, 160, 100, 30);
        add(btnRegister);

        // ✅ 登入事件
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            User user = userService.login(username, password);

            if (user != null) {
                // ✅ 登入成功
                JOptionPane.showMessageDialog(this, "✅ 登入成功！歡迎 " + user.getName());
                new MainMenuFrame(user).setVisible(true);
                dispose();
            } else {
                // ❌ 登入失敗，檢查帳號是否存在
                User checkUser = userService.findByUsername(username);

                if (checkUser == null) {
                    // ✅ 帳號不存在 → 自動跳註冊
                    int option = JOptionPane.showConfirmDialog(this,
                            "❌ 查無此帳號，是否要立即註冊？",
                            "帳號不存在", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        dispose();
                        RegisterFrame registerFrame = new RegisterFrame();
                        registerFrame.setVisible(true);

                        // ✅ 自動把剛剛輸入的帳號帶到註冊頁面
                        registerFrame.prefillUsername(username);
                    }
                } else {
                    // ✅ 帳號存在但密碼錯誤
                    JOptionPane.showMessageDialog(this, "❌ 密碼錯誤，請重新輸入！");
                }
            }
        });

        // ✅ 註冊事件：開啟註冊視窗
        btnRegister.addActionListener(e -> {
            dispose();
            new RegisterFrame().setVisible(true);
        });
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}