package org.interview.oauth.service.impl;

import com.google.api.client.http.HttpRequestFactory;
import com.google.inject.Inject;
import org.interview.oauth.api.model.response.GenericResponse;
import org.interview.oauth.api.model.response.Response;
import org.interview.oauth.domain.Twit;
import org.interview.oauth.domain.TwitBundle;
import org.interview.oauth.exception.TwitterAuthenticationException;
import org.interview.oauth.model.TwitterAuthenticationEntity;
import org.interview.oauth.repo.TwitterAuthRepository;
import org.interview.oauth.service.TwitService;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.interview.oauth.util.HttpHelper;
import org.interview.oauth.util.TwitParserUtil;

import java.util.*;
import java.util.stream.Collectors;

import static org.interview.oauth.util.AppConstants.*;

public class TwitServiceImpl implements TwitService {
    private final TwitterAuthRepository twitterAuthRepository;
    private final TwitterAuthenticator authenticator;
    private final HttpHelper httpHelper;
    private final TwitParserUtil twitParserUtil;

    @Inject
    public TwitServiceImpl(TwitterAuthRepository twitterAuthRepository, TwitterAuthenticator authenticator) {
        this.twitterAuthRepository = twitterAuthRepository;
        this.authenticator = authenticator;
        httpHelper = new HttpHelper();
        twitParserUtil = new TwitParserUtil();
    }

    @Override
    public GenericResponse<List<TwitBundle>> listTwits() {
        TwitterAuthenticationEntity authentication;
        List<TwitBundle> bundles;
        try {
            authentication = Optional.ofNullable(twitterAuthRepository.getFirst())
                    .orElseThrow(() -> new TwitterAuthenticationException("There is no auth token found, please create a session!"));
            HttpRequestFactory factory = authenticator.getFactory(authentication.getTokenSecret(), authentication.getToken());
            Map<String, String> parameters = new HashMap<>();
            parameters.put("track", "bieber");

            List<Twit> twits = httpHelper
                    .sendUrlRequestWithParams(factory, TWIT_FILTER_URL, parameters)
                    .stream()
                    .map(twitParserUtil::parseTwit)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            bundles = twitParserUtil.groupTwits(twits);

        } catch (Exception e) {
            return new GenericResponse<>(new Response(500, e.getLocalizedMessage()));
        }
        return new GenericResponse<>(Collections.unmodifiableList(bundles), new Response(200, "SUCCESS"));
    }

    public HttpHelper getHttpHelper() {
        return httpHelper;
    }

    public TwitParserUtil getTwitParserUtil() {
        return twitParserUtil;
    }
}
