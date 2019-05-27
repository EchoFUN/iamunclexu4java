package com.iamunclexu.controllers;

import com.iamunclexu.http.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
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

public class StaticController extends Controller {
    private static Logger LOGGER = LoggerFactory.getLogger(StaticController.class);

    @Override
    public HttpResponse process(HttpRequest request) {
        String uri = request.getUri();

        String basePath = System.getProperty("user.dir");
        File file = new File(basePath + uri);

        HttpResponseStatus httpResponseStatus = HttpResponseStatus.OK;
        String linesTxt = "";
        if (file.isFile() && file.exists()) {
            InputStreamReader read = null;
            try {
                read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage());
            }
            String lineTxt = "";
            BufferedReader bufferedReader = new BufferedReader(read);
            while (true) {
                try {
                    if (!((lineTxt = bufferedReader.readLine()) != null)) {
                        break;
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
                linesTxt += lineTxt;
            }
            try {
                read.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            httpResponseStatus = HttpResponseStatus.NOT_FOUND;
        }

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, Unpooled.wrappedBuffer(linesTxt.getBytes()));
        HttpHeaders headers = response.headers();

        // TODO add the static mine type to the resposne header .
        return response;
    }
}
