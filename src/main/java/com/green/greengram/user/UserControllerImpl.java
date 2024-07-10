package com.green.greengram.user;

import com.green.greengram.common.model.MyResponse;
import com.green.greengram.user.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public MyResponse<Integer> signUpPostReq(@RequestPart MultipartFile pic, @RequestPart SignUpPostReq p){
        int result = service.signUpPostReq(pic, p);
        return MyResponse.<Integer>builder()
                .resultMsg("회원가입 성공")
                .resultData(result).build();
    }

    @PostMapping("sign-in")
    public MyResponse<SignInPostRes> signInPost(HttpServletResponse res
            , @Valid @RequestBody SignInPostReq p) {
        SignInPostRes result = service.signInPost(res, p);
        log.info("SignInPostReq: {}", p);
        return MyResponse.<SignInPostRes>builder()
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
    public MyResponse<Map<String, String>> getAccessToken(HttpServletRequest req) {
        Map map = service.getAccessToken(req);

        return MyResponse.<Map<String, String>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("Access Token 발급")
                .resultData(map)
                .build();
    }

    @GetMapping
    public MyResponse<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p) {
        UserInfoGetRes result = service.getUserInfo(p);
        return MyResponse.<UserInfoGetRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }

    @PatchMapping("pic")
    public MyResponse<String> patchProfilePic(@ModelAttribute UserProfilePatchReq p) {
        String result = service.patchProfilePic(p);

        return MyResponse.<String>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }


}
