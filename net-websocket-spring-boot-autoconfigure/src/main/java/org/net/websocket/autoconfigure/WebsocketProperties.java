package org.net.websocket.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "net.websocket")
public class WebsocketProperties {

    private int port = 80;

    private int bossGroupThreads = 1;

    private int workerGroupThreads = 0;

    private String endPoint = "/ws";
}
