package builders;

import com.google.common.collect.Lists;
import org.interview.oauth.domain.Twit;
import org.interview.oauth.domain.TwitAuthor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TwitDataBuilder {

    public static List<Twit> createTwitList() {
        List<Twit> twits = new ArrayList<>();

        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        Instant theDayBeforeYesterday = now.minus(2, ChronoUnit.DAYS);

        TwitAuthor twitAuthor1 = new TwitAuthor("1", Date.from(now), "author 1", "a1");
        TwitAuthor twitAuthor2 = new TwitAuthor("2", Date.from(yesterday), "author 2", "a2");

        Twit twit1 = new Twit("1", Date.from(now), "text 1", twitAuthor1);
        Twit twit2 = new Twit("2", Date.from(yesterday), "text 2", twitAuthor1);
        Twit twit3 = new Twit("3", Date.from(theDayBeforeYesterday), "text 3", twitAuthor1);
        Twit twit4 = new Twit("4", Date.from(yesterday), "text 4", twitAuthor2);
        Twit twit5 = new Twit("5", Date.from(now), "text 5", twitAuthor2);

        twits.add(twit1);
        twits.add(twit2);
        twits.add(twit3);
        twits.add(twit4);
        twits.add(twit5);
        return twits;
    }

    public static List<String> createValidTwitLines() {

        return Lists.newArrayList("{\"created_at\":\"Sat Apr 23 21:18:36 +0000 2022\","
                + "\"id_str\":1111111111111,"
                + "\"text\":\"sample_text\","
                + "\"user\":{\"id_str\":\"1517976254506577922\","
                + "\"name\":\"test\","
                + "\"text\":\"sample_user\","
                + "\"screen_name\":\"screen_test_name\","
                + "\"created_at\":\"Sat Apr 23 21:18:36 +0000 2022\""
                + "}}");
    }

    public static List<String> createInValidTwitLines() {

        return Lists.newArrayList("");
    }
}
