package com.green.greengram.feedcomment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentGetRes {
    private long feedCommentId;
    private String comment;
    private String createdAt;
    private long writerId;
    private String writerNm;
    private String writerPic;
}
