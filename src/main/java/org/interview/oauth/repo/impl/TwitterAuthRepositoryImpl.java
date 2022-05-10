package org.interview.oauth.repo.impl;

import com.google.inject.Inject;
import org.interview.oauth.model.TwitterAuthenticationEntity;
import org.interview.oauth.repo.TwitterAuthRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TwitterAuthRepositoryImpl implements TwitterAuthRepository {

    private final Map<String, TwitterAuthenticationEntity> auths;

    @Inject
    public TwitterAuthRepositoryImpl() {
        auths = new ConcurrentHashMap<>();
    }

    @Override
    public boolean saveAuth(String tempToken, TwitterAuthenticationEntity entity) {
        validateTempToken(tempToken);
        auths.put(tempToken, entity);
        return true;
    }

    //TODO fix error handling
    private void validateTempToken(String tempToken) {
        if (auths.containsKey(tempToken)) {
            //TODO fix error handling
            throw new IllegalArgumentException("Session already exists or it is not correct!");
        }
    }

    @Override
    public List<TwitterAuthenticationEntity> getAuths() {
        return new ArrayList<>(auths.values());
    }

    @Override
    public TwitterAuthenticationEntity getFirst() {
        if (getAuths().size() == 0) {
            throw new IllegalArgumentException("There is no auth found! First create auth token!");
        }
        return getAuths().get(0);
    }
}
