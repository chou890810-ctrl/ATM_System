package service.impl;

import model.ChangeResult;
import service.ChangeService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 找零計算服務實作
 */
public class ChangeServiceImpl implements ChangeService {

    // 可用紙鈔面額
    private final int[] bills = {1000, 500, 200, 100, 50, 10};

    @Override
    public ChangeResult calculateChange(int amount) {
        Map<Integer, Integer> breakdown = new LinkedHashMap<>();

        for (int bill : bills) {
            int count = amount / bill;
            if (count > 0) {
                breakdown.put(bill, count);
                amount %= bill;
            }
        }
        return new ChangeResult(amount, breakdown);
    }
}