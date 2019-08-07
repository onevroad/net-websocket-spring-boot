package org.net.websocket.core;


import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class WebSocketRequest {

    /**
     * 通信通道上下文
     */
    private ChannelHandlerContext context;

    /**
     * 事件类型：订阅，消息，取消订阅，心跳
     */
    private String event;

    /**
     * 订阅主题
     */
    private String[] topic;

    /**
     * 消息内容
     */
    private String data;
}
