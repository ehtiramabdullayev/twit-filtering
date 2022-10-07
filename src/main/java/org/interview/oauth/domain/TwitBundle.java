package org.interview.oauth.domain;

import java.util.List;

public class TwitBundle implements Comparable<TwitBundle> {
    private final TwitAuthor author;
    private final List<Twit> twits;

    public TwitBundle(final TwitAuthor author, final List<Twit> twits) {
        this.author = author;
        this.twits = twits;
    }

    public TwitAuthor getAuthor() {
        return author;
    }

    public List<Twit> getTwits() {
        return twits;
    }

    @Override
    public int compareTo(TwitBundle o) {
        return this.author.compareTo(o.author);
    }
}
