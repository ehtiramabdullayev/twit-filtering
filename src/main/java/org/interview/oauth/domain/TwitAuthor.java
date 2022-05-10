package org.interview.oauth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitAuthor implements Comparable<TwitAuthor> {
    @JsonProperty("id_str")
    private final String idStr;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM d HH:mm:ss Z yyyy")
    private final Date createdAt;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("screen_name")
    private final String screenName;

    public TwitAuthor(
            @JsonProperty("id_str") String idStr,
            @JsonProperty("created_at") Date createdAt,
            @JsonProperty("name") String name,
            @JsonProperty("screen_name") String screenName) {
        this.idStr = idStr;
        this.createdAt = createdAt;
        this.name = name;
        this.screenName = screenName;
    }

    public String getIdStr() {
        return idStr;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(TwitAuthor o) {
        if (o == null) {
            return -1;
        }
        return this.createdAt.compareTo(o.createdAt);
    }
}
