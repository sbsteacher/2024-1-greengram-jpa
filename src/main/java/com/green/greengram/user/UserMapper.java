package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
     int signUpPostReq(SignUpPostReq p);
     User signInPost(String uid);
     UserInfoGetRes selProfileUserInfo(UserInfoGetReq p);
     int updProfilePic(UserProfilePatchReq p);
}
