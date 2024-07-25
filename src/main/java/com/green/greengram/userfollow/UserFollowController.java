package com.green.greengram.userfollow;

import com.green.greengram.common.model.MyResponse;
import com.green.greengram.userfollow.model.UserFollowDeleteReq;
import com.green.greengram.userfollow.model.UserFollowPostReq;
import com.green.greengram.userfollow.model.UserFollowReq;

public interface UserFollowController {
    MyResponse<Integer> postUserFollow(UserFollowPostReq p);
    MyResponse<Integer> deleteUserFollow(UserFollowDeleteReq p);
}
