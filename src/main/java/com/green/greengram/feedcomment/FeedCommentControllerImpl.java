package com.green.greengram.feedcomment;


import com.green.greengram.common.model.MyResponse;
import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/feed/comment")
public class FeedCommentControllerImpl implements FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    public MyResponse<Long> postFeedComment(@RequestBody FeedCommentPostReq p) {
        log.info("p: {}", p);
        long result = service.postFeedComment(p);

        return MyResponse.<Long>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("응 댓글~")
                .resultData(result)
                .build();
    }

    @GetMapping
    public MyResponse<List<FeedCommentGetRes>> getFeedCommentList(@RequestParam (name = "feed_id") long feedId) {
        List<FeedCommentGetRes> list = service.feedCommentListGet(feedId);

        return MyResponse.<List<FeedCommentGetRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(String.format("rows: %,d", list.size()))
                .resultData(list)
                .build();
    }

    @DeleteMapping
    public MyResponse<Integer> delFeedFavorite(@ModelAttribute FeedCommentDeleteReq p){
        int result = service.delFeedComment(p);

        return MyResponse.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("삭제되었습니다.")
                .resultData(result)
                .build();
    }



}
