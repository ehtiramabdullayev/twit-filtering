package org.interview.oauth.service.impl;

import com.google.api.client.http.HttpRequestFactory;
import com.google.common.collect.Lists;
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
import static com.google.api.client.http.HttpStatusCodes.STATUS_CODE_OK;
import static com.google.api.client.http.HttpStatusCodes.STATUS_CODE_SERVER_ERROR;
import static org.interview.oauth.util.AppConstants.SUCCESS;
import static org.interview.oauth.util.AppConstants.TWIT_FILTER_URL;

public class TwitServiceImpl implements TwitService {
    private final TwitterAuthRepository twitterAuthRepository;
    private final TwitterAuthenticator authenticator;

    @Inject
    public TwitServiceImpl(TwitterAuthRepository twitterAuthRepository, TwitterAuthenticator authenticator) {
        this.twitterAuthRepository = twitterAuthRepository;
        this.authenticator = authenticator;
    }

    @Override
    public GenericResponse<List<TwitBundle>> listTwits() {
        TwitterAuthenticationEntity authentication;
        List<TwitBundle> bundles = Lists.newArrayList();
        try {
            authentication = Optional.ofNullable(twitterAuthRepository.getFirst())
                    .orElseThrow(() -> new TwitterAuthenticationException("There is no auth token found, please create a session!"));
            HttpRequestFactory factory = authenticator.getFactory(authentication.getTokenSecret(), authentication.getToken());
            Map<String, String> parameters = new HashMap<>();
            parameters.put("track", "bieber");

            HttpHelper
                    .sendUrlRequestWithParams(factory, TWIT_FILTER_URL, parameters)
                    .stream()
                    .map(TwitParserUtil::parseTwit)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .sorted(Comparator.comparing(p -> p.getUser().getCreatedAt()))
                    .collect(Collectors.toList())
                    .stream()
                    .collect(Collectors.groupingBy(Twit::getUser))
                    .forEach((author, authorTwits) -> bundles.add(new TwitBundle(author, authorTwits)));

            Collections.sort(bundles);

        } catch (Exception e) {
            return new GenericResponse<>(new Response(STATUS_CODE_SERVER_ERROR, e.getLocalizedMessage()));
        }
        return new GenericResponse<>(Collections.unmodifiableList(bundles), new Response(STATUS_CODE_OK, SUCCESS));
    }
}
