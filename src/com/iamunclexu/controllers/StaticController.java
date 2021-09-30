package com.iamunclexu.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import static com.iamunclexu.confs.SysConf.FRONTEND_ROOT;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

public class StaticController extends Controller {
  private static Logger LOGGER = LoggerFactory.getLogger(StaticController.class);

  @Override
  public HttpResponse process(HttpRequest request) {
    String uri = request.uri();

    ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
    File file = new File(FRONTEND_ROOT + uri);

    HttpResponseStatus httpResponseStatus = HttpResponseStatus.OK;
    DefaultFullHttpResponse response;
    if (file.isFile() && file.exists()) {
      FileInputStream inputStream = null;
      try {
        inputStream = new FileInputStream(file);
        byte[] byteBuffer = new byte[1024];
        int length = inputStream.read(byteBuffer);

        while (length > 0) {
          outputBytes.write(byteBuffer);
          length = inputStream.read(byteBuffer);
        }
        inputStream.close();
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
      }
      response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, Unpooled.wrappedBuffer(outputBytes.toByteArray()));
    } else {
      response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.wrappedBuffer("404".getBytes()));
    }
    HttpHeaders headers = response.headers();

    if (uri.endsWith(".js")) {
      headers.add(CONTENT_TYPE, "application/javascript");
    }
    if (uri.endsWith(".css")) {
      headers.add(CONTENT_TYPE, "text/css");
    }
    String[] pictureSuffix = {"jpg", "png", "jpeg", "gif"};
    for (int i = 0; i < pictureSuffix.length; i++) {
      if (uri.endsWith("." + pictureSuffix[i])) {
        headers.add(CONTENT_TYPE, "image/" + pictureSuffix[i]);
        break;
      }
    }
    return response;
  }
}
