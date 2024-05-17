package com.green.greengram.feedcomment;


import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.feedfavorite.FeedFavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;


    @PostMapping
    public ResultDto<Long> postFeedComment(@RequestBody FeedCommentPostReq p) {
        long result = service.postFeedComment(p);

        return ResultDto.<Long>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("응 댓글~")
                .resultData(result)
                .build();
    }
}
