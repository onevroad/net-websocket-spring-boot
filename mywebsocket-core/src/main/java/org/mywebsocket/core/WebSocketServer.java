package org.mywebsocket.core;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
@Slf4j
public class WebSocketServer implements Runnable {

    private int port;
    private int bossGroupThreads = 1;
    private int workerGroupThreads = 0;
    private String endPoint = "/ws";

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupThreads);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupThreads);
        try {
            io.netty.bootstrap.ServerBootstrap b = new io.netty.bootstrap.ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketChannelInitializer(endPoint))
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(this.port).sync();
            log.info("Server was open: {}", this.port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("Server exception: {}", e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.info("Server was closed: {}", this.port);
        }
    }
}