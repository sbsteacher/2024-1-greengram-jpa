package com.green.greengram.userfollow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(UserFollowControllerImpl.class)
class UserFollowControllerTest {
    @Autowired private ObjectMapper om;
    @Autowired private MockMvc mvc;
    @MockBean private UserFollowService service;
    private final String BASE_URL = "/api/user/follow";

    @Test
    void postUserFollow() throws Exception {
        UserFollowReq p = new UserFollowReq(1, 2);
        int resultData = 1;
        given(service.postUserFollow(p)).willReturn(resultData);
        String json = om.writeValueAsString(p);

        ResultDto<Integer> expectedResult = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(resultData)
                .build();

        Map expectedResultMap = new HashMap();
        expectedResultMap.put("statusCode", HttpStatus.OK);
        expectedResultMap.put("resultMsg", HttpStatus.OK.toString());
        expectedResultMap.put("resultData", resultData);

        String expectedResultJson = om.writeValueAsString(expectedResult);
        //String expectedResultJson = om.writeValueAsString(expectedResultMap);

        ResultActions ra = mvc.perform(
                     post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
        );

        ra
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResultJson))
        .andDo(print());

        verify(service).postUserFollow(p);
    }

    @Test
    void postUserFollow2() throws Exception {
        UserFollowReq p = new UserFollowReq(1, 2);
        int resultData = 10;
        given(service.postUserFollow(p)).willReturn(resultData);
        String json = om.writeValueAsString(p);

        ResultDto<Integer> expectedResult = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(resultData)
                .build();

        Map expectedResultMap = new HashMap();
        expectedResultMap.put("statusCode", HttpStatus.OK);
        expectedResultMap.put("resultMsg", HttpStatus.OK.toString());
        expectedResultMap.put("resultData", resultData);

        String expectedResultJson = om.writeValueAsString(expectedResult);
        //String expectedResultJson = om.writeValueAsString(expectedResultMap);

        mvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResultJson))
                .andDo(print());

        verify(service).postUserFollow(p);
    }

    @Test
    void deleteUserFollow() throws Exception {
        UserFollowReq p = new UserFollowReq(4, 7);
        int resultData = 1;
        given(service.deleteUserFollow(p)).willReturn(resultData);

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("from_user_id", String.valueOf(p.getFromUserId()));
        params.add("to_user_id", String.valueOf(p.getToUserId()));

        ResultDto<Integer> expectedResult = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(resultData)
                .build();

        String expectedResultJson = om.writeValueAsString(expectedResult);

        mvc.perform(
                delete(BASE_URL).params(params)
                //delete(BASE_URL + "?from_user_id=3&to_user_id=2")
        )
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResultJson))
        .andDo(print());


        verify(service).deleteUserFollow(p);

    }
}