package org.interview.oauth.service.impl;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.inject.Inject;
import org.interview.oauth.api.model.response.GenericResponse;
import org.interview.oauth.api.model.response.Response;
import org.interview.oauth.exception.TwitterAuthenticationException;
import org.interview.oauth.model.TwitterAuthenticationEntity;
import org.interview.oauth.repo.TwitterAuthRepository;
import org.interview.oauth.service.AuthService;
import org.interview.oauth.twitter.TwitterAuthenticator;
import java.util.List;
import static com.google.api.client.http.HttpStatusCodes.STATUS_CODE_OK;
import static com.google.api.client.http.HttpStatusCodes.STATUS_CODE_SERVER_ERROR;
import static org.interview.oauth.util.AppConstants.*;

public class AuthServiceImpl implements AuthService<TwitterAuthenticationEntity> {

    private final TwitterAuthRepository twitterAuthRepository;
    private final TwitterAuthenticator authenticator;

    @Inject
    public AuthServiceImpl(TwitterAuthRepository twitterAuthRepository, TwitterAuthenticator authenticator) {
        this.twitterAuthRepository = twitterAuthRepository;
        this.authenticator = authenticator;
    }

    @Override
    public GenericResponse<String> getTwitterAuthLink() {
        try {
            return new GenericResponse<String>(authenticator.getAuthorizeUrl());
        } catch (TwitterAuthenticationException exception) {
            return new GenericResponse<>(new Response(STATUS_CODE_SERVER_ERROR, exception.getLocalizedMessage()));
        }
    }

    @Override
    public GenericResponse<String> createTwitterAuth(String pinTwitter) {
        OAuthCredentialsResponse response;
        try {
            response = authenticator.retrieveAccessTokens(pinTwitter, authenticator.getTemporaryToken());
            // now we can save our keys at one place in the db
            twitterAuthRepository.saveAuth(authenticator.getTemporaryToken(),
                    new TwitterAuthenticationEntity(CONSUMER_KEY,
                            CONSUMER_SECRET,
                            authenticator.getTemporaryToken(),
                            response.token,
                            response.tokenSecret)
            );
        } catch (Exception e) {
            return new GenericResponse<>(new Response(STATUS_CODE_SERVER_ERROR, e.getLocalizedMessage()));
        }
        return new GenericResponse<>(new Response(STATUS_CODE_OK, SUCCESS));
    }

    @Override
    public GenericResponse<List<TwitterAuthenticationEntity>> getAllAuthList() {
        List<TwitterAuthenticationEntity> auths = twitterAuthRepository.getAuths();
        return new GenericResponse<>(auths);
    }
}
