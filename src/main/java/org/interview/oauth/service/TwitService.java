package org.interview.oauth.service;

import org.interview.oauth.api.model.response.GenericResponse;
import org.interview.oauth.domain.TwitBundle;
import org.interview.oauth.exception.TwitterAuthenticationException;

import java.util.List;

public interface TwitService {
    GenericResponse<List<TwitBundle>> listTwits();
}
