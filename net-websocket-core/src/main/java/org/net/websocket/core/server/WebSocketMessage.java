package org.net.websocket.core.server;

import lombok.Data;

@Data
public class WebSocketMessage<T> {

    /**
     * 订阅主题
     */
    private String topic;

    /**
     * 消息内容
     */
    private T data;
}
