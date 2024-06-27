package com.green.greengram.userfollow;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.UserFollowReq;

public interface UserFollowController {
    ResultDto<Integer> postUserFollow(UserFollowReq p);
    ResultDto<Integer> deleteUserFollow(UserFollowReq p);
}
