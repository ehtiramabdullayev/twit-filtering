package org.interview.oauth.service;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import org.interview.oauth.api.model.response.GenericResponse;
import org.interview.oauth.exception.TwitterAuthenticationException;
import org.interview.oauth.repo.TwitterAuthRepository;
import org.interview.oauth.service.impl.AuthServiceImpl;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AuthServiceTest {
    private final AuthService authService;
    private final TwitterAuthRepository twitterAuthRepository;

    private final TwitterAuthenticator authenticator;

    public AuthServiceTest() {
        this.twitterAuthRepository = Mockito.mock(TwitterAuthRepository.class);
        this.authenticator = Mockito.mock(TwitterAuthenticator.class);
        this.authService = new AuthServiceImpl(twitterAuthRepository, authenticator);
    }

    @Test
    public void test_whenTryToGetTwitterAuthLinkSuccess() {
        GenericResponse authLink = authService.getTwitterAuthLink();
        Assert.assertEquals(200, authLink.getResponse().getStatus());
        Assert.assertEquals("SUCCESS", authLink.getResponse().getMessage());
    }

    @Test
    public void test_whenTryToCreateTwitterAuthLinkSuccess() throws TwitterAuthenticationException {
        String tempToken = "1";
        String providedPin = "pin";
        OAuthCredentialsResponse oAuthCredentialsResponse = new OAuthCredentialsResponse();

        Mockito.when(authenticator.getTemporaryToken()).thenReturn(tempToken);
        Mockito.when(authenticator.retrieveAccessTokens(Mockito.anyString(),Mockito.anyString())).thenReturn(oAuthCredentialsResponse);
        GenericResponse serviceTwitterAuth = authService.createTwitterAuth(providedPin);
        Assert.assertEquals(200, serviceTwitterAuth.getResponse().getStatus());
        Assert.assertEquals("SUCCESS", serviceTwitterAuth.getResponse().getMessage());
    }

    @Test
    public void test_whenTryToCreateTwitterAuthLinkFail() throws TwitterAuthenticationException {
        String tempToken = "1";
        String providedPin = "pin";

        OAuthCredentialsResponse oAuthCredentialsResponse = new OAuthCredentialsResponse();

        Mockito.when(authenticator.getTemporaryToken()).thenReturn(tempToken);
        Mockito.when(authenticator.retrieveAccessTokens(Mockito.anyString(),Mockito.anyString())).thenReturn(oAuthCredentialsResponse);
        Mockito.when(twitterAuthRepository.saveAuth(Mockito.anyString(),Mockito.any())).thenThrow(new IllegalArgumentException("Exception"));

        GenericResponse serviceTwitterAuth = authService.createTwitterAuth(providedPin);
        Assert.assertEquals(500, serviceTwitterAuth.getResponse().getStatus());
        Assert.assertEquals("Exception", serviceTwitterAuth.getResponse().getMessage());
    }
    @Test
    public void test_whenTryToGetAllAuthListSuccess()  {
        GenericResponse response = authService.getAllAuthList();
        Assert.assertEquals(200, response.getResponse().getStatus());
        Assert.assertEquals("SUCCESS", response.getResponse().getMessage());
    }

}
