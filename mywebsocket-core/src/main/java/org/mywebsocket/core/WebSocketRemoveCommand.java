package org.mywebsocket.core;

import io.netty.channel.ChannelHandlerContext;

public class WebSocketRemoveCommand implements Runnable {

    private ChannelHandlerContext context;

    public WebSocketRemoveCommand(ChannelHandlerContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        context.channel().close();
        WebSocketClientService.remove(this.context);
    }
}
