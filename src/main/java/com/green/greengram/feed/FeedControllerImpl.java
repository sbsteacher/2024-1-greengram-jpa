package com.green.greengram.feed;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feed.model.FeedGetReq;
import com.green.greengram.feed.model.FeedGetRes;
import com.green.greengram.feed.model.FeedPostReq;
import com.green.greengram.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/feed")
public class FeedControllerImpl {
    private final FeedService service;

    @PostMapping
    public ResultDto<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics, @RequestPart FeedPostReq p){
        FeedPostRes result= service.postFeed(pics, p);

        return ResultDto.<FeedPostRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("게시글 등록 완료")
                .resultData(result)
                .build();

    }

    @GetMapping
    public ResultDto<List<FeedGetRes>> getFeed(@ParameterObject @ModelAttribute FeedGetReq p){
        List<FeedGetRes> list = service.getFeed(p);

        return ResultDto.<List<FeedGetRes>>builder().
                statusCode(HttpStatus.OK).
                resultMsg(HttpStatus.OK.toString()).
                resultData(list).
                build();
    }
}
