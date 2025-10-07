package model;

import java.util.Map;

/**
 * 找零結果資料模型 - 用來回傳紙鈔組合
 */
public class ChangeResult {
    private int totalAmount; // 原始金額
    private Map<Integer, Integer> breakdown; // 面額 -> 張數

    public ChangeResult(int totalAmount, Map<Integer, Integer> breakdown) {
        this.totalAmount = totalAmount;
        this.breakdown = breakdown;
    }

    public int getTotalAmount() { return totalAmount; }
    public void setTotalAmount(int totalAmount) { this.totalAmount = totalAmount; }

    public Map<Integer, Integer> getBreakdown() { return breakdown; }
    public void setBreakdown(Map<Integer, Integer> breakdown) { this.breakdown = breakdown; }
}
