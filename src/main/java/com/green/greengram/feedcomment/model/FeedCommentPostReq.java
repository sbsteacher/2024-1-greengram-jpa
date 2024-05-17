package com.green.greengram.feedcomment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedCommentPostReq {
    @JsonIgnore
    private long feedCommentId;

    private long feedId;
    private long userId;
    private String comment;
}
