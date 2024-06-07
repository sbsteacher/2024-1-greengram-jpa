package com.green.greengram.feedcomment;


import com.green.greengram.common.model.ResultDto;
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
public class FeedCommentControllerImpl {
    private final FeedCommentService service;


    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResultDto<Long> postFeedComment(@RequestBody FeedCommentPostReq p) {
        long result = service.postFeedComment(p);

        return ResultDto.<Long>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("응 댓글~")
                .resultData(result)
                .build();
    }

    @DeleteMapping
    public ResultDto<Integer> delfeedFavorite(@ModelAttribute FeedCommentDeleteReq p){
        int result = service.delFeedComment(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultData(result)
                .resultMsg("")
                .build();
    }

    @GetMapping
    public ResultDto<List<FeedCommentGetRes>>feedCommentListGet(@RequestParam (name = "feed_id") long feedId) {
        List<FeedCommentGetRes> list = service.feedCommentListGet(feedId);

        return ResultDto.<List<FeedCommentGetRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultData(list)
                .resultMsg(String.format("rows: %,d",list.size()))
                .build();
    }

}
