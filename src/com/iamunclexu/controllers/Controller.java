package com.iamunclexu.controllers;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public abstract class Controller {
    public abstract HttpResponse process(HttpRequest request);
}
