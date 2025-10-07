package ui;

import dao.impl.TransactionDaoImpl;
import model.TransactionRecord;
import util.IOTool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TransactionFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private TransactionDaoImpl transactionDao;
    private int currentUserId;

    // 🔍 查詢元件
    private JComboBox<String> typeBox;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JTextField txtMin;
    private JTextField txtMax;
    private JButton btnNewButton;

    public TransactionFrame(int userId) {
        this.currentUserId = userId;
        this.transactionDao = new TransactionDaoImpl();

        setTitle("📜 交易紀錄查詢");
        setSize(842, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // 🔎 1️⃣ 查詢條件區
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        typeBox = new JComboBox<>(new String[]{"all", "deposit", "withdraw", "transfer_out", "transfer_in"});
        txtStartDate = new JTextField(8); // yyyy-MM-dd
        txtEndDate = new JTextField(8);
        txtMin = new JTextField(5);
        txtMax = new JTextField(5);

        searchPanel.add(new JLabel("類型:"));
        searchPanel.add(typeBox);
        searchPanel.add(new JLabel("起:"));
        searchPanel.add(txtStartDate);
        searchPanel.add(new JLabel("迄:"));
        searchPanel.add(txtEndDate);
        searchPanel.add(new JLabel("金額:"));
        searchPanel.add(txtMin);
        searchPanel.add(new JLabel("~"));
        searchPanel.add(txtMax);

        JButton btnSearch = new JButton("🔍 查詢");
        searchPanel.add(btnSearch);

        // ✅ 新增「📤 匯出 CSV」按鈕
        JButton btnExport = new JButton("📤 匯出報表");
        searchPanel.add(btnExport);

        getContentPane().add(searchPanel, BorderLayout.NORTH);
        
        btnNewButton = new JButton("清空報表");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int confirm = JOptionPane.showConfirmDialog(null, "確定要清空交易紀錄嗎？", "確認", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    IOTool.clearLog(); // ✅ 呼叫清空紀錄的方法
                    boolean success = transactionDao.clearTransactionsByUserId(currentUserId);
                    tableModel.setRowCount(0);
                    JOptionPane.showMessageDialog(null, "✅ 報表已清空！");
                }
        	}
        	
        });
        searchPanel.add(btnNewButton);

        // 📊 2️⃣ 表格區
        String[] columnNames = {"交易編號", "交易類型", "金額", "交易時間"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 📦 預設載入所有紀錄
        loadTransactionData(transactionDao.findByUserId(currentUserId));

        // 🔎 查詢按鈕事件
        btnSearch.addActionListener(e -> searchTransactions());

        // ✅ 📤 匯出 CSV 事件
        btnExport.addActionListener(e -> exportToCSV());
    }

    /**
     * 📊 載入交易紀錄到表格
     */
    private void loadTransactionData(List<TransactionRecord> transactions) {
        tableModel.setRowCount(0); // 清空表格
        for (TransactionRecord tx : transactions) {
            tableModel.addRow(new Object[]{
                    tx.getId(),
                    tx.getType(),
                    tx.getAmount(),
                    tx.getTime()
            });
        }
    }

    /**
     * 🔍 查詢邏輯（呼叫 DAO 的 searchTransactions 方法）
     */
    private void searchTransactions() {
        try {
            String type = (String) typeBox.getSelectedItem();

            LocalDate startDate = txtStartDate.getText().isEmpty() ? null : LocalDate.parse(txtStartDate.getText());
            LocalDate endDate = txtEndDate.getText().isEmpty() ? null : LocalDate.parse(txtEndDate.getText());
            Double min = txtMin.getText().isEmpty() ? null : Double.parseDouble(txtMin.getText());
            Double max = txtMax.getText().isEmpty() ? null : Double.parseDouble(txtMax.getText());

            List<TransactionRecord> results = transactionDao.searchTransactions(
                    currentUserId,
                    type,
                    startDate,
                    endDate,
                    min,
                    max
            );

            loadTransactionData(results);

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "查無符合條件的紀錄！", "結果", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ 查詢失敗，請確認輸入格式（日期需為 yyyy-MM-dd）", "錯誤", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * 📤 匯出報表成 CSV
     */
    private void exportToCSV() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "⚠️ 沒有可匯出的資料", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("選擇匯出位置");
        fileChooser.setSelectedFile(new File("交易紀錄報表.csv"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                 PrintWriter writer = new PrintWriter(osw)) {

                // ✅ 寫入 BOM，讓 Excel 認得 UTF-8
                writer.write('\uFEFF');

                // 寫入表頭
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    writer.print(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) writer.print(",");
                }
                writer.println();

                // 寫入資料列
                for (int r = 0; r < tableModel.getRowCount(); r++) {
                    for (int c = 0; c < tableModel.getColumnCount(); c++) {
                        writer.print(tableModel.getValueAt(r, c));
                        if (c < tableModel.getColumnCount() - 1) writer.print(",");
                    }
                    writer.println();
                }

                JOptionPane.showMessageDialog(this, "✅ 匯出成功！檔案位置：" + file.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ 匯出失敗：" + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // ✅ 測試用 main()
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TransactionFrame(1).setVisible(true));
    }
}
