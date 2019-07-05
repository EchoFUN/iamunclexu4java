package com.iamunclexu.confs;

import com.iamunclexu.controllers.AboutController;
import com.iamunclexu.controllers.CommentController;
import com.iamunclexu.controllers.Controller;
import com.iamunclexu.controllers.HomeController;
import com.iamunclexu.controllers.NotFoundController;
import com.iamunclexu.controllers.PostController;
import com.iamunclexu.controllers.StaticController;
import com.iamunclexu.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.iamunclexu.confs.RequestUrl.URL_ABOUT;
import static com.iamunclexu.confs.RequestUrl.URL_COMMENT;
import static com.iamunclexu.confs.RequestUrl.URL_HOME;
import static com.iamunclexu.confs.RequestUrl.URL_POST_DETAILS;

public class RequestConf {

    Map<String, Controller> requestContainer = new HashMap<>();

    private static RequestConf requestMap;
    private StaticController staticController;
    NotFoundController notFoundController;

    public static RequestConf inst() {
        if (requestMap == null) {
            requestMap = new RequestConf();
        }
        return requestMap;
    }

    public void init() {
        requestContainer.put(URL_HOME, new HomeController());
        requestContainer.put(URL_POST_DETAILS, new PostController());
        requestContainer.put(URL_ABOUT, new AboutController());
        requestContainer.put(URL_COMMENT, new CommentController());
        staticController = new StaticController();
        notFoundController = new NotFoundController();
    }

    public String extractPureUri(String uri) {
        String pureUri = uri;

        if (uri.contains("?")) {
            String[] tokens = uri.split("\\?");
            if (tokens.length > 1) {
                pureUri = tokens[0];
            }
        }
        return pureUri;
    }

    public Map<String, String> extractQuery(String uri) {
        Map<String, String> queryMap = new HashMap<>();

        Pattern pattern = Pattern.compile("([^?=&]+)(=([^&]*))?");
        Matcher matcher = pattern.matcher(uri);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(3);   // TODO In case of any inject Risks !
            if (key != null && value != null) {



                queryMap.put(key, value);
            }
        }
        return queryMap;
    }

    public Controller fetchControllerByUrl(String uri) {
        String updatedUri = extractPureUri(uri);

        for (Map.Entry<String, Controller> entry : requestContainer.entrySet()) {
            if (entry.getKey().equals(updatedUri)) {           // TODO the route rules can be more completed .
                Controller controller = entry.getValue();
                controller.setQueryData(extractQuery(uri));
                return controller;
            }
        }

        // Execute the static files .
        if (Utils.isStaticUri(uri)) {
            return staticController;
        }
        return notFoundController;
    }
}
