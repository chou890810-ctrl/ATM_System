package ui;

import javax.swing.*;
import java.awt.*;

public class ChangeDialog extends JDialog {

    public ChangeDialog(Frame owner, boolean modal, double amount) {
        super(owner, "找零工具", modal);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ✅ 這裡直接使用提款金額來顯示找零計算結果
        JTextArea txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setFont(new Font("微軟正黑體", Font.PLAIN, 16));

        String result = calculateChange(amount);
        txtResult.setText("提款金額：" + amount + " 元\n\n找零組合：\n" + result);

        add(new JScrollPane(txtResult), BorderLayout.CENTER);

        JButton btnClose = new JButton("關閉");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);
    }

    // ✅ 計算找零的邏輯（可以調整）
    private String calculateChange(double amount) {
        int[] coins = {1000, 500, 100, 50, 10, 5, 1};
        StringBuilder sb = new StringBuilder();
        int remaining = (int) amount;

        for (int coin : coins) {
            int count = remaining / coin;
            if (count > 0) {
                sb.append(coin).append(" 元 × ").append(count).append("\n");
                remaining %= coin;
            }
        }

        return sb.toString();
    }
}
