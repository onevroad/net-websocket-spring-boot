package org.net.websocket.core.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.net.websocket.core.server.WebSocketHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private String endPoint;

    public WebSocketChannelInitializer(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //HttpServerCodec: 针对http协议进行编解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //ChunkedWriteHandler分块写处理，文件过大会将内存撑爆
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        //作用是将一个Http的消息组装成一个HttpRequest或者HttpResponse, 该Handler必须放在HttpServerCodec后的后面
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
        //用于处理websocket, /ws为访问websocket时的uri
        pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler(endPoint));
        //自定义处理器
        pipeline.addLast("myWebSocketHandler", new WebSocketHandler());
    }
}