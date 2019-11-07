package org.net.websocket.autoconfigure;

import lombok.Data;

@Data
public class WebsocketRetryProperties {

    private boolean enable = false;

    private int maxRetryTime = 3;

    private long retryInterval = 1000;
}
