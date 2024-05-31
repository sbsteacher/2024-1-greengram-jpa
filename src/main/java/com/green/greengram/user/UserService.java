package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int signUpPostReq(MultipartFile pic, SignUpPostReq p);
    SignInPostRes signInPost (SignInPostReq p);
    UserInfoGetRes getUserInfo(UserInfoGetReq p);
    String patchProfilePic(UserProfilePatchReq p);
}
