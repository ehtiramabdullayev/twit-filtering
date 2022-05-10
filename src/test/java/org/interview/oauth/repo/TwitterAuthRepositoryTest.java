package org.interview.oauth.repo;

import org.interview.oauth.model.TwitterAuthenticationEntity;
import org.interview.oauth.repo.impl.TwitterAuthRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TwitterAuthRepositoryTest {
    @Test(expected = IllegalArgumentException.class)
    public void test_whenTryInsertSameTokenFail() {
        TwitterAuthRepository authRepository = new TwitterAuthRepositoryImpl();
        String tempToken = "1";
        TwitterAuthenticationEntity entity =
                new TwitterAuthenticationEntity("key",
                        "secret",
                        tempToken,
                        "token",
                        "tokenSecret");
        authRepository.saveAuth(tempToken, entity);
        authRepository.saveAuth(tempToken, entity);
    }

    @Test
    public void test_whenTryToSaveAuthSuccess() {
        TwitterAuthRepository authRepository = new TwitterAuthRepositoryImpl();
        String tempToken = "1";
        TwitterAuthenticationEntity entity =
                new TwitterAuthenticationEntity("key",
                        "secret",
                        tempToken,
                        "token",
                        "tokenSecret");
        Assert.assertTrue(authRepository.saveAuth(tempToken, entity));
    }

    @Test
    public void test_whenTryGetAuthListSuccess() {
        TwitterAuthRepository authRepository = new TwitterAuthRepositoryImpl();
        String tempToken = "1";
        TwitterAuthenticationEntity entity =
                new TwitterAuthenticationEntity("key",
                        "secret",
                        tempToken,
                        "token",
                        "tokenSecret");
        authRepository.saveAuth(tempToken, entity);

        List<TwitterAuthenticationEntity> auths = authRepository.getAuths();
        Assert.assertEquals("token", auths.get(0).getToken());
        Assert.assertEquals(tempToken, auths.get(0).getTemporaryToken());
        Assert.assertEquals("key", auths.get(0).getConsumerKey());
        Assert.assertEquals("secret", auths.get(0).getConsumerSecret());
        Assert.assertEquals(1, auths.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_whenTryToGetFirstFails() {
        TwitterAuthRepository authRepository = new TwitterAuthRepositoryImpl();
        String tempToken = "1";
        TwitterAuthenticationEntity entity =
                new TwitterAuthenticationEntity("key",
                        "secret",
                        tempToken,
                        "token",
                        "tokenSecret");
        authRepository.getFirst();
    }

    @Test
    public void test_whenTryToGetFirstSuccess() {
        TwitterAuthRepository authRepository = new TwitterAuthRepositoryImpl();
        String tempToken = "1";
        TwitterAuthenticationEntity entity =
                new TwitterAuthenticationEntity("key",
                        "secret",
                        tempToken,
                        "token",
                        "tokenSecret");
        authRepository.saveAuth(tempToken, entity);
        TwitterAuthenticationEntity first = authRepository.getFirst();
        Assert.assertEquals(entity, first);
    }
}
