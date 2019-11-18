package org.net.websocket.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "net.websocket.retry")
public class WebsocketRetryProperties {

    private boolean enable = false;

    private int maxRetryTime = 3;

    private long retryInterval = 1000;
}
