package com.green.greengram.feedcomment;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.MyResponse;
import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/feed/comment")
@Tag(name = "피드 댓글")
public class FeedCommentControllerImpl implements FeedCommentController {

    private final FeedCommentService service;
    private final ObjectMapper om;

    @PostMapping
    public void postFeedComment(HttpServletResponse res, @RequestBody FeedCommentPostReq p) throws IOException {
        log.info("p: {}", p);
        //long result = service.postFeedComment(p);
        long result = 10;


        MyResponse<Long> obj = MyResponse.<Long>builder()
                        .statusCode(HttpStatus.OK)
                        .resultMsg("응~ 댓글~")
                        .resultData(result)
                        .build();

        String json = om.writeValueAsString(obj);

        res.resetBuffer();
        res.setStatus(200);
        res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding("UTF-8");
        res.getOutputStream().write(json.getBytes("UTF-8"));
        res.flushBuffer();
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
