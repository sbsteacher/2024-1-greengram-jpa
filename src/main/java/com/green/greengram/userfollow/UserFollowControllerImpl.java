package com.green.greengram.userfollow;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/follow")
public class UserFollowControllerImpl implements UserFollowController {

    private final UserFollowService service;

    @Override
    @PostMapping
    public ResultDto<Integer> postUserFollow(@RequestBody UserFollowReq p) {
        int result = service.postUserFollow(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }

    @Override
    @DeleteMapping
    public ResultDto<Integer> deleteUserFollow(@ParameterObject @ModelAttribute UserFollowReq p) {
        int result = service.deleteUserFollow(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }
}
