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

import java.util.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({CharEncodingConfiguration.class })
@WebMvcTest({ FeedFavoriteControllerImpl.class })
class FeedFavoriteControllerTest {

    @Autowired private ObjectMapper om;
    @Autowired private MockMvc mvc;
    @MockBean private FeedFavoriteService service;

    @Test
    void toggleReq() throws Exception {
        //given
        FeedFavoriteReq p = new FeedFavoriteReq(1, 2);

        int resultData = 1;
        given(service.toggleReq(p)).willReturn(resultData);

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("feed_id", String.valueOf(p.getFeedId()));
        params.add("user_id", String.valueOf(p.getUserId()));

        Map<String, Object> result = new HashMap();
        result.put("statusCode", HttpStatus.OK);
        result.put("resultMsg", "좋아요");
        result.put("resultData", resultData);

        String expectedResJson = om.writeValueAsString(result);

        //when
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
    void toggleReq2() throws Exception {
        //given
        FeedFavoriteReq p = new FeedFavoriteReq(1, 2);

        int resultData = 2;
        given(service.toggleReq(p)).willReturn(resultData);

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("feed_id", String.valueOf(p.getFeedId()));
        params.add("user_id", String.valueOf(p.getUserId()));

        Map<String, Object> result = new HashMap();
        result.put("statusCode", HttpStatus.OK);
        result.put("resultMsg", "좋아요");
        result.put("resultData", resultData);

        String expectedResJson = om.writeValueAsString(result);

        //when
        mvc.perform(
            get("/api/feed/favorite").params(params)
        )
        //then
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResJson))
        .andDo(print());

        verify(service).toggleReq(p);
    }
}