package org.interview.oauth.service;

public interface JsonParsingService {
    <T> T fromJSonToPOJO(String jsonString, Class<T> classType);
    String toJsonPOJO(Object data);
}
