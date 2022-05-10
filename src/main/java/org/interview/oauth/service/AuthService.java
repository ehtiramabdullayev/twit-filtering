package org.interview.oauth.service;

import org.interview.oauth.api.model.response.GenericResponse;

import java.util.List;

public interface AuthService<T> {
    GenericResponse<String> getTwitterAuthLink();
    GenericResponse<String> createTwitterAuth(String pinTwitter);
    GenericResponse<List<T>> getAllAuthList();
}
