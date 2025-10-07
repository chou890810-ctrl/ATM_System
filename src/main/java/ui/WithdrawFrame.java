package ui;

import model.User;
import service.AtmService;
import service.impl.AtmServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 💸 提款視窗 - 輸入金額並執行提款
 */
public class WithdrawFrame extends JFrame {

    private JTextField txtAmount;
    private User currentUser;
    private AtmService atmService = new AtmServiceImpl();
    private Double lastWithdrawAmount = null;
    public WithdrawFrame(User user) {
        this.currentUser = user;
        setTitle("提款作業");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("請輸入提款金額：");
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 18));
        lblTitle.setBounds(100, 30, 200, 30);
        getContentPane().add(lblTitle);

        txtAmount = new JTextField();
        txtAmount.setBounds(100, 80, 200, 30);
        getContentPane().add(txtAmount);

        JButton btnWithdraw = new JButton("提款");
        btnWithdraw.setBounds(100, 130, 90, 30);
        getContentPane().add(btnWithdraw);

        JButton btnCancel = new JButton("取消");
        btnCancel.setBounds(210, 130, 90, 30);
        getContentPane().add(btnCancel);

        

    }
}
