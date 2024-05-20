package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper {
    int insUserFollow(UserFollowReq p);
    int delUserFollow(UserFollowReq p);
}
