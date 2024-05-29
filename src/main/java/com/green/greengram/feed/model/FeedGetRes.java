package com.green.greengram.feed.model;

//import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private long feedId;
    private String contents;
    private String location;
    private String createdAt;
    private long writerId;
    private String writerNm;
    private String writerPic;
    private int isFav;

    private List<String> pics;
    private List<FeedCommentGetRes> comments;
    private int isMoreComment;
}
