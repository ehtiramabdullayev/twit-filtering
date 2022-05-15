package org.interview.oauth.util;

import builders.TwitDataBuilder;
import org.interview.oauth.domain.Twit;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class TwitParserUtilTest {
    private final TwitParserUtil parserUtil;

    public TwitParserUtilTest() {
        this.parserUtil = new TwitParserUtil();
    }

    @Test
    public void testIfParseTwitIsValidSuccessful() {
        Optional<String> first = TwitDataBuilder.createValidTwitLines().stream().findFirst();
        Optional<Twit> twit = parserUtil.parseTwit(first.get());
        Assert.assertEquals("1517976254506577922", twit.get().getUser().getIdStr());
        Assert.assertEquals("test", twit.get().getUser().getName());
        Assert.assertEquals("screen_test_name", twit.get().getUser().getScreenName());
        Assert.assertEquals("sample_text", twit.get().getText());
        Assert.assertEquals("1111111111111", twit.get().getIdStr());
    }

    @Test
    public void testIfParseTwitsIsInvalidSuccessful() {
        List<String> twitLines = TwitDataBuilder.createInValidTwitLines();
        Assert.assertEquals(Optional.empty(), parserUtil.parseTwit(twitLines.get(0)));
    }
}