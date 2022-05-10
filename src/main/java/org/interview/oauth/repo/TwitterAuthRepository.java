package org.interview.oauth.repo;

import org.interview.oauth.model.TwitterAuthenticationEntity;

import java.util.List;

public interface TwitterAuthRepository {
    boolean saveAuth(String tempToken, TwitterAuthenticationEntity entity);
    List<TwitterAuthenticationEntity> getAuths();
    TwitterAuthenticationEntity getFirst();
}
