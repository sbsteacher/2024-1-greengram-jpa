package com.green.greengram.feedfavorite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.userfollow.UserFollowControllerImpl;
import com.green.greengram.userfollow.UserFollowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@Import({CharEncodingConfiguration.class })
@WebMvcTest({FeedFavoriteControllerImpl.class })
class FeedFavoriteControllerTest {

    @Autowired private ObjectMapper om;
    @Autowired private MockMvc mvc;
    @MockBean
    private UserFollowService service;

    @Test
    void toggleReq() {
    }
}