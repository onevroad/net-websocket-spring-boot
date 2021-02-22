package org.net.websocket.samples;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.net.websocket.annotation.WebSocketScan;
import org.net.websocket.core.server.WebSocketMessagePublisher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
@WebSocketScan(basePackages = {"org.net.websocket.samples.handler"})
public class WebSocketApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebSocketApplication.class).run(args);
        test();
    }

    private static void test() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("test-%d").build());
        executor.scheduleAtFixedRate(() -> {
            WebSocketMessagePublisher.publish("test","test-123");
        }, 0, 1, TimeUnit.SECONDS);
    }
}
