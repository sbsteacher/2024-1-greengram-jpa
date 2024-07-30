package com.green.greengram.feedcomment.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class FeedCommentGetRes  {
    private Long feedCommentId;
    private String comment;
    private String createdAt;
    private Long writerId;
    private String writerNm;
    private String writerPic;
}
