package org.interview.oauth;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.interview.oauth.controller.TwitterOperationsController;
import org.interview.oauth.module.ApplicationModule;

import static spark.Spark.*;

public class Main {
    public static final int MAIN_PORT = 5555;
//    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(MAIN_PORT);
        before((request, response) -> response.type("application/json"));
        initRoutes();
    }

    public static void initRoutes() {
        Injector injector = Guice.createInjector(new ApplicationModule());
        TwitterOperationsController controller = injector.getInstance(TwitterOperationsController.class);
        get("/hello", (req, res) -> "Hello Sytac");
        get("/authLink", controller::getTwitterAuthUrl);
        post("/createSession", controller::createTwitterSession);
        get("/sessionList", controller::getSessionList);
        get("/listTwits", controller::getTwitsList);
    }
}
