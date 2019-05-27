package com.iamunclexu.controllers;

import java.util.Map;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public abstract class Controller {
    Map<String, String> queryData;

    public abstract HttpResponse process(HttpRequest request);

    public void setQueryData(Map<String, String> queryData) {
        this.queryData = queryData;
    }
}
