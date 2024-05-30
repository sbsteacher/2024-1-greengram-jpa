package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({ FeedCommentServiceImpl.class })
class FeedCommentServiceTest {

    @MockBean FeedCommentMapper mapper;
    @Autowired FeedCommentService service;


    @Test
    @DisplayName("댓글 등록")
    void postFeedComment() {
        // given - when - then
        FeedCommentPostReq p1 = new FeedCommentPostReq();
        p1.setFeedCommentId(10);
        long result1 = service.postFeedComment(p1);
        assertEquals(p1.getFeedCommentId(), result1);

        FeedCommentPostReq p2 = new FeedCommentPostReq();
        p2.setFeedCommentId(19);
        long result2 = service.postFeedComment(p2);
        assertEquals(p2.getFeedCommentId(), result2);

        verify(mapper).postFeedComment(p1);
        verify(mapper).postFeedComment(p2);
    }

    @Test
    @DisplayName("댓글 삭제")
    void delFeedComment() {
        FeedCommentDeleteReq p1 = new FeedCommentDeleteReq(10, 20);
        FeedCommentDeleteReq p2 = new FeedCommentDeleteReq(100, 200);

        given(mapper.delFeedComment(p1)).willReturn(1);
        given(mapper.delFeedComment(p2)).willReturn(2);

        long result1 = service.delFeedComment(p1);
        assertEquals(1, result1, "1. 리턴값 상이");
        long result2 = service.delFeedComment(p2);
        assertEquals(2, result2, "2. 리턴값 상이");

        verify(mapper, times(1)).delFeedComment(p1);
        verify(mapper, times(1)).delFeedComment(p2);
    }

    @Test
    void feedCommentListGet() {
    }
}