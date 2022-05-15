package org.interview.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.oauth.domain.Twit;

import java.util.Optional;

public class TwitParserUtil {

    public Optional<Twit> parseTwit(String string) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Optional.of(mapper.readValue(string, Twit.class));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

}
