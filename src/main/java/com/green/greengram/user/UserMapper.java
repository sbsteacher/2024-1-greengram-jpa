package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
     List<User> selTest(long userId);

     int signUpPostReq(SignUpPostReq p);
     User signInPost(String uid);
     UserInfoGetRes selProfileUserInfo(UserInfoGetReq p);
     int updProfilePic(UserProfilePatchReq p);
}
