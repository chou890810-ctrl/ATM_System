package dao;

import java.util.List;
import model.TransactionRecord;

public interface TransactionDao {
    boolean insert(TransactionRecord record);        // 新增交易紀錄
    List<TransactionRecord> findByUserId(int userId); // 查詢某使用者的所有交易紀錄
}
