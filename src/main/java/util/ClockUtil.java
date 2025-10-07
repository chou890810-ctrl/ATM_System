package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 🕐 ClockUtil - 系統時間工具類
 * 提供全域時間格式化功能
 */
public class ClockUtil {

    /**
     * 取得目前時間（完整格式）
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String now() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(fmt);
    }

    /**
     * 取得目前日期（只有年月日）
     * @return yyyy-MM-dd
     */
    public static String today() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(fmt);
    }

    /**
     * 取得目前時間（只有時分秒）
     * @return HH:mm:ss
     */
    public static String time() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalDateTime.now().format(fmt);
    }
}

