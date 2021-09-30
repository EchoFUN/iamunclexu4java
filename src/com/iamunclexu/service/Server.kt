/**
 * Main Method of the service .
 *
 *
 *
 *
 *
 *
 *
 * @author XU Kai(xukai.ken@gmail.com)
 */

package com.iamunclexu.service

import com.iamunclexu.confs.RequestConf
import com.iamunclexu.confs.SysConf.WEB_PORT
import com.iamunclexu.confs.TemplateConf
import com.iamunclexu.database.DBUtils
import com.iamunclexu.http.HttpHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpRequestDecoder
import io.netty.handler.codec.http.HttpResponseEncoder
import org.slf4j.LoggerFactory

class Server(private val port: Int) {

  fun start() {
    val bootstrap = ServerBootstrap()
    val masterGroup = NioEventLoopGroup()
    val slaveGroup = NioEventLoopGroup()
    bootstrap.group(masterGroup, slaveGroup).channel(NioServerSocketChannel::class.java)
      .option(ChannelOption.SO_BACKLOG, 128)
      .childOption(ChannelOption.SO_KEEPALIVE, java.lang.Boolean.TRUE)

    bootstrap.childHandler(object : ChannelInitializer<SocketChannel>() {
      public override fun initChannel(ch: SocketChannel) {
        LOGGER.info("initChannel ch:$ch")

        val pipeline = ch.pipeline()
        pipeline.addLast("decoder", HttpRequestDecoder())
        pipeline.addLast("encoder", HttpResponseEncoder())
        pipeline.addLast(
          "aggregator",
          HttpObjectAggregator(512 * 1024)
        )  // 聚合类，方便后续使用的 FullHttpRequest 。
        pipeline.addLast("handler", HttpHandler())
      }
    })

    try {
      bootstrap.bind(port).sync()
      // ChannelFuture channelFuture = bootstrap.bind(port).sync();

      // TODO do not know why this method will be invoked ?
      // channelFuture.channel().closeFuture().sync();
    } catch (e: InterruptedException) {
      masterGroup.shutdownGracefully()
      slaveGroup.shutdownGracefully()
      LOGGER.error(e.message)
    }

  }

  companion object {
    private val LOGGER = LoggerFactory.getLogger(Server::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
      init()
      Server(WEB_PORT).start()
      LOGGER.info("Service started at the port of $WEB_PORT")
    }

    private fun init() {
      DBUtils.inst().init()
      RequestConf.inst().init()
      TemplateConf.inst().init()
    }
  }
}
