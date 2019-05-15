package com.iamunclexu.confs;

import com.iamunclexu.controllers.Controller;
import com.iamunclexu.controllers.HelloworldController;
import com.iamunclexu.controllers.HomeController;

import java.util.HashMap;
import java.util.Map;

import static com.iamunclexu.confs.RequestUrl.URL_HELLO_WORLD;
import static com.iamunclexu.confs.RequestUrl.URL_HOME;

public class RequestConf {

    Map<String, Controller> requestContainer = new HashMap<>();

    private static RequestConf requestMap;

    public static RequestConf inst() {
        if (requestMap == null) {
            requestMap = new RequestConf();
        }
        return requestMap;
    }

    public void init() {
        requestContainer.put(URL_HOME, new HomeController());
        requestContainer.put(URL_HELLO_WORLD, new HelloworldController());
    }

    public Controller fetchControllerByUrl(String uri) {
        for (Map.Entry<String, Controller> entry : requestContainer.entrySet()) {
            if (entry.getKey().equals(uri)) {      // TODO the route rules can be more completed .
                return entry.getValue();
            }
        }
        return null;
    }
}
