package com.green.greengram.feedfavorite.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedFavoriteReq {
    private long feedId;
    private long userId;

}
