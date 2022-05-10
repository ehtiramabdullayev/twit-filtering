/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ________  __    __  ________    ____       ______   *
 * /_/_/_/_/ /_/   /_/ /_/_/_/_/  _/_/_/_   __/_/_/_/   *
 * /_/_____  /_/___/_/    /_/    /_/___/_/  /_/          *
 * /_/_/_/_/   /_/_/_/    /_/    /_/_/_/_/  /_/           *
 * ______/_/       /_/    /_/    /_/   /_/  /_/____        *
 * /_/_/_/_/       /_/    /_/    /_/   /_/    /_/_/_/ . io  *
 * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package org.interview.oauth.twitter;

import com.google.api.client.auth.oauth.*;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.inject.Inject;
import org.interview.oauth.exception.TwitterAuthenticationException;

import java.io.IOException;

import static org.interview.oauth.util.AppConstants.CONSUMER_KEY;
import static org.interview.oauth.util.AppConstants.CONSUMER_SECRET;
import static org.interview.oauth.util.AppConstants.AUTHORIZE_URL;
import static org.interview.oauth.util.AppConstants.ACCESS_TOKEN_URL;
import static org.interview.oauth.util.AppConstants.REQUEST_TOKEN_URL;

public class TwitterAuthenticator {
    private final String consumerKey;
    private final String consumerSecret;
    private String temporaryToken;
    public final HttpTransport TRANSPORT;


    @Inject
    public TwitterAuthenticator() {
        this.consumerKey = CONSUMER_KEY;
        this.consumerSecret = CONSUMER_SECRET;
        TRANSPORT = new NetHttpTransport();
    }

    public String getAuthorizeUrl() throws TwitterAuthenticationException {
        OAuthHmacSigner signer = new OAuthHmacSigner();
        signer.clientSharedSecret = consumerSecret;

        OAuthCredentialsResponse requestTokenResponse = getTemporaryToken(signer);
        signer.tokenSharedSecret = requestTokenResponse.tokenSecret;

        OAuthAuthorizeTemporaryTokenUrl authorizeUrl = new OAuthAuthorizeTemporaryTokenUrl(AUTHORIZE_URL);
        authorizeUrl.temporaryToken = requestTokenResponse.token;

        this.temporaryToken = authorizeUrl.temporaryToken;
        return authorizeUrl.build();
    }

    private OAuthCredentialsResponse getTemporaryToken(final OAuthHmacSigner signer) throws TwitterAuthenticationException {
        OAuthGetTemporaryToken requestToken = new OAuthGetTemporaryToken(REQUEST_TOKEN_URL);
        requestToken.consumerKey = consumerKey;
        requestToken.transport = TRANSPORT;
        requestToken.signer = signer;

        OAuthCredentialsResponse requestTokenResponse;
        try {
            requestTokenResponse = requestToken.execute();
        } catch (IOException e) {
            throw new TwitterAuthenticationException("Unable to acquire temporary token: " + e.getMessage(), e);
        }

        return requestTokenResponse;
    }


    private OAuthCredentialsResponse retrieveAccessTokens(final String providedPin, final OAuthHmacSigner signer, final String token) throws TwitterAuthenticationException {
        OAuthGetAccessToken accessToken = new OAuthGetAccessToken(ACCESS_TOKEN_URL);
        accessToken.verifier = providedPin;
        accessToken.consumerKey = consumerSecret;
        accessToken.signer = signer;
        accessToken.transport = TRANSPORT;
        accessToken.temporaryToken = token;

        OAuthCredentialsResponse accessTokenResponse;
        try {
            accessTokenResponse = accessToken.execute();
        } catch (IOException e) {
            throw new TwitterAuthenticationException("Unable to authorize access: " + e.getMessage(), e);
        }
        return accessTokenResponse;
    }

    public OAuthCredentialsResponse retrieveAccessTokens(final String providedPin, String temporaryToken) throws TwitterAuthenticationException {
        OAuthHmacSigner signer = new OAuthHmacSigner();
        signer.clientSharedSecret = consumerSecret;
        signer.tokenSharedSecret = temporaryToken;
        return retrieveAccessTokens(providedPin, signer, temporaryToken);

    }

    public HttpRequestFactory getFactory(String tokenSharedSecret, String token) {
        OAuthHmacSigner signer = new OAuthHmacSigner();
        OAuthParameters parameters = new OAuthParameters();

        signer.clientSharedSecret = consumerSecret;
        signer.tokenSharedSecret = tokenSharedSecret;
        parameters.consumerKey = consumerKey;
        parameters.token = token;
        parameters.signer = signer;

        return TRANSPORT.createRequestFactory(parameters);
    }

    public String getTemporaryToken() {
        return temporaryToken;
    }
}
