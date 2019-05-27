/**
 * Main Method of the service .
 *
 *
 *
 *
 *
 * @author XU Kai(xukai.ken@gmail.com)
 */

package com.iamunclexu.service;

import com.iamunclexu.confs.RequestConf;
import com.iamunclexu.confs.TemplateConf;
import com.iamunclexu.database.DBUtils;
import com.iamunclexu.http.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class Server {
    private static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            LOGGER.error("No any of the port specified !");
            return;
        }
        int port = Integer.parseInt(args[0]);
        init();
        new Server(port).start();
        LOGGER.info("Service started at the port of " + port);
    }

    public static void init() {
        DBUtils.inst().init();
        RequestConf.inst().init();
        TemplateConf.inst().init();
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup masterGroup = new NioEventLoopGroup();
        NioEventLoopGroup slaveGroup = new NioEventLoopGroup();
        bootstrap.group(masterGroup, slaveGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                LOGGER.info("initChannel ch:" + ch);

                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("decoder", new HttpRequestDecoder());
                pipeline.addLast("encoder", new HttpResponseEncoder());
                pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));  // 聚合类，方便后续使用的 FullHttpRequest 。
                pipeline.addLast("handler", new HttpHandler());
            }
        });

        try {
            bootstrap.bind(port).sync();
            // ChannelFuture channelFuture = bootstrap.bind(port).sync();

            // TODO do not know why this method will be invoked ?
            // channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            masterGroup.shutdownGracefully();
            slaveGroup.shutdownGracefully();
            LOGGER.error(e.getMessage());
        }
    }
}