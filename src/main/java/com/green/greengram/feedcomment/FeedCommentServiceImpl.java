package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentServiceImpl implements FeedCommentService {
    private final FeedCommentMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public long postFeedComment(FeedCommentPostReq p) {
        p.setUserId(authenticationFacade.getLoginUserId());
        mapper.postFeedComment(p);
        return p.getFeedCommentId();
    }

    @Override
    public int delFeedComment(FeedCommentDeleteReq p){
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        return mapper.delFeedComment(p);
    }

    @Override
    public List<FeedCommentGetRes> feedCommentListGet(long feedId) {
        return mapper.feedCommentList(feedId);
    }
}
