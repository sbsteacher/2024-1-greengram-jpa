package com.green.greengram.user;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.user.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserControllerImpl {
    private final UserServiceImpl service;

    @PostMapping("sign-up")
    public ResultDto<Integer> signUpPostReq(@RequestPart MultipartFile pic, @RequestPart SignUpPostReq p){
        int result = service.signUpPostReq(pic, p);
        return ResultDto.<Integer>builder()
                .resultMsg("회원가입 성공")
                .resultData(result).build();
    }

    @PostMapping("sign-in")
    public ResultDto<SignInPostRes> signInPost(HttpServletResponse res, @RequestBody SignInPostReq p) {
        SignInPostRes result = service.signInPost(res, p);

        return ResultDto.<SignInPostRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("로그인 성공")
                .resultData(result)
                .build();
    }

    /*
    FE는 단지 get방식으로 아무런 작업없이 단순히 요청만하면 refreshToken이 넘어온다.
    이유는 우리가 refreshToken을 로그인을 성공하면 cookie에 담았기 때문
    cookie는 요청마다 항상 넘어온다.
     */
    @GetMapping("access-token")
    public ResultDto<Map<String, String>> getAccessToken(HttpServletRequest req) {
        Map map = service.getAccessToken(req);

        return ResultDto.<Map<String, String>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("Access Token 발급")
                .resultData(map)
                .build();
    }

    @GetMapping
    public ResultDto<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p) {
        UserInfoGetRes result = service.getUserInfo(p);
        return ResultDto.<UserInfoGetRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }

    @PatchMapping("pic")
    public ResultDto<String> patchProfilePic(@ModelAttribute UserProfilePatchReq p) {
        String result = service.patchProfilePic(p);

        return ResultDto.<String>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }


}
