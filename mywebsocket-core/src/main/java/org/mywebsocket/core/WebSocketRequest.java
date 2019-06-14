package org.mywebsocket.core;


import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class WebSocketRequest {

    private ChannelHandlerContext context;
    private String event;
    private String[] topic;
    private String data;
}
