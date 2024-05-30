package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({ UserFollowServiceImpl.class })
class UserFollowServiceTest {

    @MockBean
    private UserFollowMapper mapper;

    @Autowired
    private UserFollowService service;

    //given - when - then
    @Test
    void postUserFollow() {
        UserFollowReq p1 = new UserFollowReq(1, 2);
        UserFollowReq p2 = new UserFollowReq(1, 3);
        UserFollowReq p3 = new UserFollowReq(1, 4);

        given(mapper.insUserFollow(p1)).willReturn(0);
        given(mapper.insUserFollow(p2)).willReturn(1);
        given(mapper.insUserFollow(p3)).willReturn(2);

        assertEquals(0, service.postUserFollow(p1));
        assertEquals(1, service.postUserFollow(p2));
        assertEquals(2, service.postUserFollow(p3));

        verify(mapper).insUserFollow(p1);
        verify(mapper).insUserFollow(p2);
        verify(mapper).insUserFollow(p3);
    }

    @Test
    void deleteUserFollow() {
        UserFollowReq p1 = new UserFollowReq(1, 2);
        UserFollowReq p2 = new UserFollowReq(1, 3);
        UserFollowReq p3 = new UserFollowReq(1, 4);

        given(mapper.delUserFollow(p1)).willReturn(0);
        given(mapper.delUserFollow(p2)).willReturn(1);
        given(mapper.delUserFollow(p3)).willReturn(2);

        assertEquals(0, service.deleteUserFollow(p1));
        assertEquals(1, service.deleteUserFollow(p2));
        assertEquals(2, service.deleteUserFollow(p3));

        verify(mapper).delUserFollow(p1);
        verify(mapper).delUserFollow(p2);
        verify(mapper).delUserFollow(p3);
    }
}