package com.green.greengram.feedcomment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
@EqualsAndHashCode
public class FeedCommentDeleteReq {
    private long feedCommentId;

    @JsonIgnore
    private long signedUserId;

    @ConstructorProperties({ "feed_comment_id" })
    public FeedCommentDeleteReq(long feedCommentId) {
        this.feedCommentId = feedCommentId;
    }

    public void setSignedUserId(long signedUserId) {
        this.signedUserId = signedUserId;
    }
}
