package service;

/**
 * 時鐘服務介面
 */
public interface ClockService {
    /**
     * 取得目前時間字串
     * @return 時間（格式：yyyy-MM-dd HH:mm:ss）
     */
    String getCurrentTime();
}