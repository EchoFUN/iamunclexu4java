/**
 * Main Method of the service .
 *
 * @author XU Kai(xukai.ken@gmail.com)
 */

package com.iamunclexu.service;

import com.iamunclexu.http.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
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
        try {
            new Server(port).start();
            LOGGER.info("Service started at the port of " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                LOGGER.debug("initChannel ch:" + ch);

                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("decoder", new HttpRequestDecoder());
                pipeline.addLast("encoder", new HttpResponseEncoder());
                pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
                pipeline.addLast("handler", new HttpHandler());
            }
        });

        try {
            bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}