package org.interview.oauth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Twit implements Comparable<Twit> {
    @JsonProperty("id_str")
    private final String idStr;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM d HH:mm:ss Z yyyy")
    private final Date createdAt;
    @JsonProperty("text")
    private final String text;
    @JsonProperty("user")
    private final TwitAuthor user;

    public Twit(@JsonProperty("id_str") String idStr,
                @JsonProperty("created_at") Date createdAt,
                @JsonProperty("text") String text,
                @JsonProperty("user") TwitAuthor user) {
        this.idStr = idStr;
        this.createdAt = createdAt;
        this.text = text;
        this.user = user;
    }

    public String getIdStr() {
        return idStr;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public TwitAuthor getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(Twit o) {
        if (o == null) {
            return -1;
        }
        return this.createdAt.compareTo(o.createdAt);
    }
}
