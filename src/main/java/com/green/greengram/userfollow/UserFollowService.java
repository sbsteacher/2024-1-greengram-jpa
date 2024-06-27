package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowReq;

public interface UserFollowService {
    int postUserFollow(UserFollowReq p);
    int deleteUserFollow(UserFollowReq p);
}
