package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.feedfavorite.FeedFavoriteMapper;
import com.green.greengram.feedfavorite.model.FeedFavoriteReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    public long postFeedComment(FeedCommentPostReq p) {
        mapper.postFeedComment(p);

        return p.getFeedCommentId();
    }

    public int delFeedComment(FeedCommentDeleteReq p){
        return mapper.delFeedComment(p);
    }
    List<FeedCommentGetRes> feedCommentListGet(long feedId) {
        return mapper.feedCommentList(feedId);
    }
}
