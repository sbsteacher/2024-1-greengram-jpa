package com.green.greengram.feedfavorite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.feedfavorite.model.FeedFavoriteReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({CharEncodingConfiguration.class })
@WebMvcTest({ FeedFavoriteControllerImpl.class })
class FeedFavoriteControllerTest2 {

    @Autowired private ObjectMapper om;
    @Autowired private MockMvc mvc;
    @MockBean private FeedFavoriteService service;

    void proc(FeedFavoriteReq p, Map<String, Object> result) throws Exception {
        int resultData = (int)result.get("resultData");
        given(service.toggleReq(p)).willReturn(resultData);

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("feed_id", String.valueOf(p.getFeedId()));
        params.add("user_id", String.valueOf(p.getUserId()));

        String expectedResJson = om.writeValueAsString(result);

        mvc.perform(
            get("/api/feed/favorite").params(params)
        )
        //then
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResJson))
        .andDo(print());

        verify(service).toggleReq(p);
    }

    @Test
    void toggleReq() throws Exception {
        //given
        FeedFavoriteReq p = new FeedFavoriteReq(1, 2);
        int resultData = 1;
        Map<String, Object> result = new HashMap();
        result.put("statusCode", HttpStatus.OK);
        result.put("resultMsg", "좋아요");
        result.put("resultData", resultData);

        proc(p, result);
    }


    @Test
    void toggleReq2() throws Exception {
        FeedFavoriteReq p = new FeedFavoriteReq(2, 4);
        int resultData = 2;
        Map<String, Object> result = new HashMap();
        result.put("statusCode", HttpStatus.OK);
        result.put("resultMsg", "좋아요");
        result.put("resultData", resultData);

        proc(p, result);
    }

    @Test
    void toggleReq3() throws Exception {
        //resultMsg값이 "좋아요 취소"가 리턴이 되는지 확인

        FeedFavoriteReq p = new FeedFavoriteReq(3, 5);
        int resultData = 0;
        Map<String, Object> result = new HashMap();
        result.put("statusCode", HttpStatus.OK);
        result.put("resultMsg", "좋아요 취소");
        result.put("resultData", resultData);

        proc(p, result);

    }
}