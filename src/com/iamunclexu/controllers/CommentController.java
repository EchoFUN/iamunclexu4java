package com.iamunclexu.controllers;

import com.iamunclexu.database.CommentModel;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import static com.iamunclexu.confs.Constant.CONTENT_TYPE_HTML;
import static com.iamunclexu.confs.Constant.SQL_UPDATED;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

public class CommentController extends Controller {

    private CommentModel commentModel = new CommentModel();

    @Override
    public HttpResponse process(HttpRequest request) {
        String flagger = commentModel.saveComment(this.queryData);

        DefaultFullHttpResponse response;
        String result;
        if (flagger.equals(SQL_UPDATED)) {
            result = "{\"code\":0}";
        } else {
            result = "{\"code\":1,msg:\"" + flagger + "\"}";
        }
        response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(result.getBytes()));
        response.headers().set(CONTENT_TYPE, "application/json");
        return response;
    }
}
