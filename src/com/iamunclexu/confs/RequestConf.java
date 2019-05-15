package configrations;

import com.iamunclexu.controllers.Controller;

import java.util.HashMap;
import java.util.Map;

import handlers.Controller;
import handlers.HelloworldController;
import handlers.NotFoundController;

import static com.iamunclexu.confs.RequestUrl.URL_HELLO_WORLD;
import static request.RequestUrl.URL_HELLO_WORLD;

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
        requestContainer.put(URL_HELLO_WORLD, new HelloworldController());
    }

    public Controller fetchControllerByUrl(String uri) {
        for (Map.Entry<String, Controller> entry : requestContainer.entrySet()) {
            if (entry.getKey().equals(uri)) {
                return entry.getValue();
            }
        }
        return new NotFoundController();
    }
}
