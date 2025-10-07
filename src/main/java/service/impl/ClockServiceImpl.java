package service.impl;

import service.ClockService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 時鐘服務實作
 */
public class ClockServiceImpl implements ClockService {

    @Override
    public String getCurrentTime() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(fmt);
    }
}
