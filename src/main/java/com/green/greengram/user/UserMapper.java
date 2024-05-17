package com.green.greengram.user;

import com.green.greengram.user.model.SignUpPostReq;
import com.green.greengram.user.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
     int signUpPostReq(SignUpPostReq p);
     User signInPost(String uid);
}
