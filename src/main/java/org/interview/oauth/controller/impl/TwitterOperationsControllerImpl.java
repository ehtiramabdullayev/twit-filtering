package org.interview.oauth.controller.impl;

import org.interview.oauth.api.model.request.TwitterCreateSessionRequest;
import org.interview.oauth.api.model.response.GenericResponse;
import org.interview.oauth.controller.TwitterOperationsController;
import org.interview.oauth.domain.TwitBundle;
import org.interview.oauth.model.TwitterAuthenticationEntity;
import org.interview.oauth.service.AuthService;
import org.interview.oauth.service.JsonParsingService;
import org.interview.oauth.service.TwitService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.List;

public class TwitterOperationsControllerImpl implements TwitterOperationsController {
    private final AuthService<TwitterAuthenticationEntity> authService;
    private final JsonParsingService jsonParsingService;
    private final TwitService twitService;

    @Inject
    public TwitterOperationsControllerImpl(AuthService authService,
                                           JsonParsingService jsonParsingService,
                                           TwitService twitService) {
        this.authService = authService;
        this.jsonParsingService = jsonParsingService;
        this.twitService = twitService;
    }

    @Override
    public String getTwitterAuthUrl(Request request, Response response) {
        GenericResponse<String> genericResponse = authService.getTwitterAuthLink();
        response.status(genericResponse.getResponse().getStatus());
        return jsonParsingService.toJsonPOJO(genericResponse);
    }

    @Override
    public String createTwitterSession(Request request, Response response) {
        TwitterCreateSessionRequest accountRequest = jsonParsingService.fromJSonToPOJO(request.body(), TwitterCreateSessionRequest.class);
        GenericResponse<String> genericResponse = authService.createTwitterAuth(accountRequest.getPinCode());
        response.status(genericResponse.getResponse().getStatus());
        return jsonParsingService.toJsonPOJO(genericResponse);
    }

    @Override
    public String getSessionList(Request request, Response response) {
        GenericResponse<List<TwitterAuthenticationEntity>> genericResponse = authService.getAllAuthList();
        response.status(genericResponse.getResponse().getStatus());
        return jsonParsingService.toJsonPOJO(genericResponse);
    }

    @Override
    public String getTwitsList(Request request, Response response) {
        GenericResponse<List<TwitBundle>> listGenericResponse = twitService.listTwits();
        response.status(listGenericResponse.getResponse().getStatus());
        return jsonParsingService.toJsonPOJO(listGenericResponse);
    }
}
