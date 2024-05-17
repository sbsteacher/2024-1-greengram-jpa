package com.green.greengram.feed;

import com.green.greengram.feed.model.*;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import org.apache.catalina.Cluster;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FeedMapper {
    int postFeed(FeedPostReq p);
    int postFeedPics(FeedPicPostDto p);

    List<FeedGetRes> getFeed(FeedGetReq p);
    List<String> getFeedPicsByFeedId(long feedId);
    List<FeedCommentGetRes> getFeedComment(long feedId);
}
