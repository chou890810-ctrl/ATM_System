package ui;

import javax.swing.*;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtName;
    private UserService userService = new UserServiceImpl();

    public RegisterFrame() {
        setTitle("會員註冊");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("📋 註冊會員");
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblTitle.setBounds(110, 20, 150, 30);
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

        JLabel lblName = new JLabel("姓名：");
        lblName.setBounds(50, 150, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(130, 150, 150, 25);
        add(txtName);

        JButton btnRegister = new JButton("註冊");
        btnRegister.setBounds(110, 200, 100, 30);
        add(btnRegister);

        // ✅ 註冊按鈕事件
        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String name = txtName.getText().trim();

            if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ 所有欄位都要填寫！");
                return;
            }

            User newUser = new User(0, username, password, name, 0.0);
            boolean success = userService.register(newUser);

            if (success) {
                JOptionPane.showMessageDialog(this, "✅ 註冊成功！請回登入畫面登入");
                dispose(); // 關閉註冊視窗
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "❌ 帳號已存在，請重新輸入！");
            }
        });
    }

	public void prefillUsername(String username) {
		// TODO Auto-generated method stub
		
	}
}