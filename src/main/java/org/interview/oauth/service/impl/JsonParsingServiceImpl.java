package org.interview.oauth.service.impl;

import com.google.gson.Gson;
import org.interview.oauth.service.JsonParsingService;

public class JsonParsingServiceImpl implements JsonParsingService {
    @Override
    public <T> T fromJSonToPOJO(String json, Class<T> classType) {
        return new Gson().fromJson(json, classType);
    }

    @Override
    public String toJsonPOJO(Object data) {
        return new Gson().toJson(data);
    }
}
