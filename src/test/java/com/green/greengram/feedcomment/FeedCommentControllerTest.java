package com.green.greengram.feedcomment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({ CharEncodingConfiguration.class })
@WebMvcTest({ FeedCommentControllerImpl.class })
class FeedCommentControllerTest {

    @Autowired private ObjectMapper om;
    @Autowired private MockMvc mvc;
    @MockBean private FeedCommentService service;

    @Test
    void postFeedComment() throws Exception {
        FeedCommentPostReq p = new FeedCommentPostReq();
        p.setFeedId(1);
        p.setUserId(2);
        p.setComment("안녕");

        long resultData = 1;

        String reqJson = om.writeValueAsString(p);

        given(service.postFeedComment(p)).willReturn(resultData);

        Map<String, Object> expectedRes = new HashMap();
        expectedRes.put("statusCode", HttpStatus.OK);
        expectedRes.put("resultMsg", "응 댓글~");
        expectedRes.put("resultData", resultData);

        String expectedResJson = om.writeValueAsString(expectedRes);

        mvc.perform(
            post("/api/feed/comment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqJson)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResJson))
        .andDo(print());

        verify(service).postFeedComment(p);
    }

    @Test
    void postFeedComment2() throws Exception {
        FeedCommentPostReq p = new FeedCommentPostReq();
        p.setFeedId(2);
        p.setUserId(3);
        p.setComment("반가워");

        long resultData = 5;

        String reqJson = om.writeValueAsString(p);

        given(service.postFeedComment(p)).willReturn(resultData);

        Map<String, Object> expectedRes = new HashMap();
        expectedRes.put("statusCode", HttpStatus.OK);
        expectedRes.put("resultMsg", "응 댓글~");
        expectedRes.put("resultData", resultData);

        String expectedResJson = om.writeValueAsString(expectedRes);

        mvc.perform(
                        post("/api/feed/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(reqJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson))
                .andDo(print());

        verify(service).postFeedComment(p);
    }

    @Test
    void getFeedCommentList() {
    }

    @Test
    void delFeedFavorite() {
    }
}