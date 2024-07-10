package com.green.greengram.userfollow;

import com.green.greengram.common.model.MyResponse;
import com.green.greengram.userfollow.model.UserFollowReq;

public interface UserFollowController {
    MyResponse<Integer> postUserFollow(UserFollowReq p);
    MyResponse<Integer> deleteUserFollow(UserFollowReq p);
}
