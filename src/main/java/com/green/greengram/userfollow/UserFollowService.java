package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowDeleteReq;
import com.green.greengram.userfollow.model.UserFollowPostReq;

public interface UserFollowService {
    int postUserFollow(UserFollowPostReq p);
    int deleteUserFollow(UserFollowDeleteReq p);
}
