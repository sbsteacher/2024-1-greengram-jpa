package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

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

        verify(mapper, times(1)).postFeedComment(p1);
        verify(mapper, times(1)).postFeedComment(p2);
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
    @DisplayName("댓글 리스트")
    void feedCommentListGet() {
        List<FeedCommentGetRes> list1 = new ArrayList();

        FeedCommentGetRes res1 = new FeedCommentGetRes();
        FeedCommentGetRes res2 = new FeedCommentGetRes();
        list1.add(res1);
        list1.add(res2);
        
        res1.setFeedCommentId(10);
        res1.setComment("res1");
        res1.setCreatedAt("2024-05-30 12:52:02");
        res1.setWriterId(100);
        res1.setWriterNm("홍길동");
        res1.setWriterPic("홍길동.jpg");

        res2.setFeedCommentId(20);
        res2.setComment("res2");
        res2.setCreatedAt("2024-05-30 12:54:02");
        res2.setWriterId(200);
        res2.setWriterNm("남길동");
        res2.setWriterPic("남길동.jpg");

        List<FeedCommentGetRes> list2 = new ArrayList();
        long paramFeedId1 = 5;
        long paramFeedId2 = 7;
        given(mapper.feedCommentList(paramFeedId1)).willReturn(list1);
        given(mapper.feedCommentList(paramFeedId2)).willReturn(list2);


        List<FeedCommentGetRes> list3 = new ArrayList();
        FeedCommentGetRes res10 = new FeedCommentGetRes();
        FeedCommentGetRes res11 = new FeedCommentGetRes();

        list3.add(res10);
        list3.add(res11);

        res10.setFeedCommentId(10);
        res10.setComment("res1");
        res10.setCreatedAt("2024-05-30 12:52:02");
        res10.setWriterId(100);
        res10.setWriterNm("홍길동");
        res10.setWriterPic("홍길동.jpg");

        res11.setFeedCommentId(20);
        res11.setComment("res2");
        res11.setCreatedAt("2024-05-30 12:54:02");
        res11.setWriterId(200);
        res11.setWriterNm("남길동");
        res11.setWriterPic("남길동.jpg");


        List<FeedCommentGetRes> result1 = service.feedCommentListGet(paramFeedId1);
        assertEquals(list1, result1, "1. 리턴값이 다름");
        assertEquals(list3, result1, "1-2. 리턴값이 다름");

        List<FeedCommentGetRes> result2 = service.feedCommentListGet(paramFeedId2);
        assertEquals(list2.size(), result2.size(), "2. 리턴값이 다름");

        verify(mapper).feedCommentList(paramFeedId1);
        verify(mapper).feedCommentList(paramFeedId2);
    }
}