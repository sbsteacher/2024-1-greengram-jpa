package com.green.greengram.feedcomment;

import com.green.greengram.common.model.MyResponse;
import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import java.util.List;

public interface FeedCommentController {
    MyResponse<Long> postFeedComment(FeedCommentPostReq p);
    MyResponse<List<FeedCommentGetRes>> getFeedCommentList(long feedId);
    MyResponse<Integer> delFeedFavorite(FeedCommentDeleteReq p);
}
