package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.feedfavorite.FeedFavoriteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private  final FeedCommentMapper mapper;

    public long postFeedComment(FeedCommentPostReq p) {
        mapper.postFeedComment(p);

        return p.getFeedCommentId();
    }
}
