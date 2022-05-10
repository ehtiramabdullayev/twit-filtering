package org.interview.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.oauth.domain.Twit;
import org.interview.oauth.domain.TwitAuthor;
import org.interview.oauth.domain.TwitBundle;

import java.util.*;
import java.util.stream.Collectors;

public class TwitParserUtil {

    public List<TwitBundle> groupTwits(List<Twit> twits) {
        List<TwitBundle> groupedTwits = new ArrayList<>();
        Map<TwitAuthor, List<Twit>> twitsGroupedByUser = twits.stream().collect(Collectors.groupingBy(Twit::getUser));
        twitsGroupedByUser.forEach((author, authorTwits) -> {
            Collections.sort(authorTwits);
            groupedTwits.add(new TwitBundle(author, authorTwits));
        });
        Collections.sort(groupedTwits);
        return groupedTwits;
    }

    public Optional<Twit> parseTwit(String string) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Optional.of(mapper.readValue(string, Twit.class));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

}
