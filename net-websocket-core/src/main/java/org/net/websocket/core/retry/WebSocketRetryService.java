package org.net.websocket.core.retry;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebSocketRetryService {

    private static boolean isEnabled = false;
    private static int maxRetryTime = 3;
    private static long retryInterval = 1000;
    private static ScheduledThreadPoolExecutor errorRetryExecutor = null;
    private static ScheduledThreadPoolExecutor notFoundRetryExecutor = null;

    public static void enable() {
        isEnabled = true;
        errorRetryExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("error-retry-send-%d").build());
        errorRetryExecutor.scheduleAtFixedRate(new WebSocketErrorRetryCommand(), 0, retryInterval, TimeUnit.MILLISECONDS);
        notFoundRetryExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("not-found-retry-send-%d").build());
        notFoundRetryExecutor.scheduleAtFixedRate(new WebSocketNotFoundRetryCommand(), 0, retryInterval, TimeUnit.MILLISECONDS);
    }

    public static void enable(int maxRetryTime) {
        WebSocketRetryService.maxRetryTime = maxRetryTime;
        enable();
    }

    public static void enable(long retryInterval) {
        WebSocketRetryService.retryInterval = retryInterval;
        enable();
    }

    public static void enable(int maxRetryTime, long retryInterval) {
        WebSocketRetryService.maxRetryTime = maxRetryTime;
        WebSocketRetryService.retryInterval = retryInterval;
        enable();
    }

    public static void retry(ErrorRetryMessage message) {
        if (isEnabled) {
            ErrorRetryQueue.add(message);
        }
    }

    public static void retry(NotFoundRetryMessage message) {
        if (isEnabled) {
            NotFoundRetryQueue.add(message);
        }
    }

    public static boolean isContinueRetry(int retryTime) {
        return retryTime <= maxRetryTime;
    }

    public static boolean isRetryTime(long lastSendTime) {
        return System.currentTimeMillis() - lastSendTime >= retryInterval;
    }

    public static ScheduledThreadPoolExecutor getErrorRetryExecutor() {
        return errorRetryExecutor;
    }

    public static ScheduledThreadPoolExecutor getNotFoundRetryExecutor() {
        return notFoundRetryExecutor;
    }
}
