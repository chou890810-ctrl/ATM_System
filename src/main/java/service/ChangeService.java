package service;

import model.ChangeResult;

/**
 * 找零計算服務介面
 */
public interface ChangeService {
    /**
     * 根據金額計算找零紙鈔組合
     * @param amount 金額
     * @return 找零結果
     */
    ChangeResult calculateChange(int amount);
}