package org.net.websocket.samples;

import org.net.websocket.annotation.WebSocketScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;


@EnableAutoConfiguration
@WebSocketScan(basePackages = {"org.net.websocket.samples.handler"})
public class WebSocketApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebSocketApplication.class).run(args);
    }
}
