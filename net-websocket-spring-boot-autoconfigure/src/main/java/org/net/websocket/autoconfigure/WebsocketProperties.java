package org.net.websocket.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "net.websocket")
@EnableConfigurationProperties(WebsocketRetryProperties.class)
public class WebsocketProperties {

    private int port = 80;

    private int bossGroupThreads = 1;

    private int workerGroupThreads = 0;

    private String endPoint = "/ws";

    private WebsocketRetryProperties retry;
}
