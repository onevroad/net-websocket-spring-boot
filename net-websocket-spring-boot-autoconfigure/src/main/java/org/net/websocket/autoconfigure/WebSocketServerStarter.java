package org.net.websocket.autoconfigure;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.net.websocket.core.retry.WebSocketRetryService;
import org.net.websocket.core.server.WebSocketServerService;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class WebSocketServerStarter {

    private WebsocketProperties properties;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("websocket-server-%d").build());


    public WebSocketServerStarter(WebsocketProperties properties) {
        this.properties = properties;
    }

    public void start() {
        executor.execute(() -> WebSocketServerService.start(properties.getPort(), properties.getBossGroupThreads(), properties.getWorkerGroupThreads(), properties.getEndPoint()));
        if (properties.getRetry().isEnable()) {
            WebSocketRetryService.enable(properties.getRetry().getMaxRetryTime(), properties.getRetry().getRetryInterval());
        }
    }

}
