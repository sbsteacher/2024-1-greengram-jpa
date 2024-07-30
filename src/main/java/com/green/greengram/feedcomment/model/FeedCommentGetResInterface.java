package com.green.greengram.feedcomment.model;

public interface FeedCommentGetResInterface {
    Long getFeedCommentId();
    String getComment();
    String getCreatedAt();
    Long getWriterId();
    String getWriterNm();
    String getWriterPic();
}
