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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(UserFollowControllerImpl.class)
class UserFollowControllerTest {
    @Autowired private ObjectMapper om;
    @Autowired private MockMvc mvc;
    @MockBean private UserFollowServiceImpl service;

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

        mvc.perform(
            MockMvcRequestBuilders
                    .post("/api/user/follow")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)

        )
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResultJson))
        .andDo(print());

        verify(service).postUserFollow(p);
    }

    @Test
    void deleteUserFollow() {
    }
}