package org.net.websocket.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebSocketRetryService {

    private static boolean isEnabled = false;
    private static int maxRetryTime = 3;
    private static long retryInterval = 1000;

    public static void enable() {
        isEnabled = true;
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("retry-send-%d").build());
        executor.scheduleAtFixedRate(new WebSocketRetryCommand(), 0, retryInterval, TimeUnit.MILLISECONDS);
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

    public static void retry(RetryMessage message) {
        if (isEnabled) {
            RetryQueue.add(message);
        }
    }
}
