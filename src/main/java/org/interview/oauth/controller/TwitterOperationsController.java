package org.interview.oauth.controller;

import spark.Request;
import spark.Response;

public interface TwitterOperationsController {
    String getTwitterAuthUrl(Request request, Response response);
    String createTwitterSession(Request request, Response response);
    String getSessionList(Request request, Response response);
    String getTwitsList(Request request, Response response);
}
