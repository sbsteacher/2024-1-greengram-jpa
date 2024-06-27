package com.green.greengram.feedcomment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import java.util.List;

public interface FeedCommentController {
    ResultDto<Long> postFeedComment(FeedCommentPostReq p);
    ResultDto<List<FeedCommentGetRes>> getFeedCommentList(long feedId);
    ResultDto<Integer> delFeedFavorite(FeedCommentDeleteReq p);
}
