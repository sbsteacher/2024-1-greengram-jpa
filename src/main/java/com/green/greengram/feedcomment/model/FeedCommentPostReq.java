package com.green.greengram.feedcomment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class FeedCommentPostReq {
    @JsonIgnore
    private long feedCommentId;

    private long feedId;
    @JsonIgnore
    private long userId;
    private String comment;

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
