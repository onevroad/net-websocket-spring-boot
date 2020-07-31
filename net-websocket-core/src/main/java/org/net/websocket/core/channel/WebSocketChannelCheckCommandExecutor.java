package org.net.websocket.core.channel;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class WebSocketChannelCheckCommandExecutor {

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("channel-check-%d").build());

    private static void execute(Runnable command) {
        executor.scheduleAtFixedRate(command, 0, 60, TimeUnit.SECONDS);
    }

    public static void start() {
        execute(new WebSocketChannelCheckCommand());
    }
}
