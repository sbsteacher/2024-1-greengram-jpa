package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface
FeedCommentMapper {
    void postFeedComment(FeedCommentPostReq p);
}
