package com.green.greengram.feedfavorite.model;


import lombok.*;

import java.beans.ConstructorProperties;

@Getter
@ToString
@EqualsAndHashCode
public class FeedFavoriteReq {
    private long feedId; //feed_id
    private long userId; //user_id

    @ConstructorProperties({ "feed_id", "user_id" })
    public FeedFavoriteReq(long feedId, long userId) {
        this.feedId = feedId;
        this.userId = userId;
    }
}
