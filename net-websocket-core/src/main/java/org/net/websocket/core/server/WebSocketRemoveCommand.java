package org.net.websocket.core.server;

import io.netty.channel.ChannelHandlerContext;
import org.net.websocket.core.client.WebSocketClientService;

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
