package org.mywebsocket.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;


public class WebSocketCommandExecutor {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("websocket-command-%d").build());

    public static void execute(Runnable command) {
        executor.execute(command);
    }
}
