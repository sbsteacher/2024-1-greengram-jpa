package com.green.greengram.feedcomment.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
@EqualsAndHashCode
public class FeedCommentDeleteReq {
    private long feedCommentId;
    private long signedUserId;

    @ConstructorProperties({ "feed_comment_id", "signed_user_id" })
    public FeedCommentDeleteReq(long feedCommentId, long signedUserId) {
        this.feedCommentId = feedCommentId;
        this.signedUserId = signedUserId;
    }
}
