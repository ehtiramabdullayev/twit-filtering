package org.interview.oauth.util;

import builders.TwitDataBuilder;
import org.interview.oauth.domain.Twit;
import org.interview.oauth.domain.TwitAuthor;
import org.interview.oauth.domain.TwitBundle;
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
    public void testIfGroupingSuccessful() {
        List<Twit> twitList = TwitDataBuilder.createTwitList();
        List<TwitBundle> groupTwits = parserUtil.groupTwits(twitList);
        TwitAuthor author = groupTwits.get(0).getAuthor();
        Assert.assertEquals("2", author.getIdStr());
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
        Assert.assertEquals(Optional.empty(),parserUtil.parseTwit(twitLines.get(0)));;
    }
}