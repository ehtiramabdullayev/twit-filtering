package org.interview.oauth.service;

import org.interview.oauth.api.model.response.GenericResponse;
import org.interview.oauth.domain.TwitBundle;
import org.interview.oauth.repo.TwitterAuthRepository;
import org.interview.oauth.service.impl.TwitServiceImpl;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class TwitServiceTest {
    private final TwitterAuthRepository twitterAuthRepository;

    private final TwitterAuthenticator authenticator;
    private final TwitServiceImpl twitService;

    public TwitServiceTest() {
        this.twitterAuthRepository = Mockito.mock(TwitterAuthRepository.class);
        this.authenticator = Mockito.mock(TwitterAuthenticator.class);
        this.twitService = new TwitServiceImpl(twitterAuthRepository, authenticator);
    }

    @Test
    public void test_whenTryToGetTwitListFail() {
        GenericResponse<List<TwitBundle>> genericResponse = twitService.listTwits();
        Assert.assertEquals(500, genericResponse.getResponse().getStatus());
        Assert.assertEquals("There is no auth token found, please create a session!", genericResponse.getResponse().getMessage());
    }
}
