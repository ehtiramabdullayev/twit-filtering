package org.interview.oauth.model;

public class TwitterAuthenticationEntity {
    private final String consumerKey;
    private final String consumerSecret;
    private final String temporaryToken;
    private final String token;
    private final String tokenSecret;

    public TwitterAuthenticationEntity(final String consumerKey,
                                       final String consumerSecret,
                                       final String temporaryToken,
                                       final String token,
                                       final String tokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.temporaryToken = temporaryToken;
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public String getTemporaryToken() {
        return temporaryToken;
    }
}
