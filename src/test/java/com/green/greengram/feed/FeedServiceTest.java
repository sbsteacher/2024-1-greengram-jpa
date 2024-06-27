package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.feed.model.*;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({ FeedServiceImpl.class })
@TestPropertySource(
    properties = {
            "file.directory=D:/2024-01/download/greengram_tdd/"
    }
)
class FeedServiceTest {
    @Value("${file.directory}") String uploadPath;
    @MockBean FeedMapper mapper;
    @MockBean CustomFileUtils customFileUtils;
    @Autowired FeedService service;

    @Test
    void postFeed() throws Exception {
        FeedPostReq p = new FeedPostReq();
        //feedId 값 주입시켜줘야 한다. 왜??
        p.setFeedId(10);

        List<MultipartFile> pics = new ArrayList();
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "a.jpg", "image/jpg",
                new FileInputStream(String.format("%stest/a.jpg", uploadPath))
        );

        MultipartFile fm2 = new MockMultipartFile(
                "pic", "b.jpg", "image/jpg",
                new FileInputStream(String.format("%stest/b.jpg", uploadPath))
        );
        pics.add(fm1);
        pics.add(fm2);
        String randomFileNm1 = "a1b2.jpg";
        String randomFileNm2 = "c1d2.jpg";
        String[] randomFileArr = { randomFileNm1, randomFileNm2 };

        given(customFileUtils.makeRandomFileName(fm1)).willReturn(randomFileNm1);
        given(customFileUtils.makeRandomFileName(fm2)).willReturn(randomFileNm2);

        FeedPostRes res = service.postFeed(pics, p);
        verify(mapper).postFeed(p);
        String path = String.format("feed/%d", p.getFeedId());
        verify(customFileUtils).makeFolders(path);

        FeedPicPostDto dto = FeedPicPostDto.builder()
                .feedId(p.getFeedId())
                .build();
        for(int i=0; i<pics.size(); i++) {
            MultipartFile pic = pics.get(i);
            verify(customFileUtils).makeRandomFileName(pic);
            String fileNm = randomFileArr[i];
            String target = String.format("%s/%s" , path, fileNm);
            dto.getFileNames().add(fileNm);
            verify(customFileUtils).transferTo(pic, target);
        }
        verify(mapper).postFeedPics(dto);
        FeedPostRes watedRes = FeedPostRes.builder()
                .feedId(dto.getFeedId())
                .pics(dto.getFileNames())
                .build();

        assertEquals(watedRes, res, "리턴값이 다름");
    }

    @Test
    void getFeed() {
        FeedGetReq p = new FeedGetReq(1, 10, 2L, 3L);
        List<FeedGetRes> list = new ArrayList();
        FeedGetRes fgr1 = new FeedGetRes();
        FeedGetRes fgr2 = new FeedGetRes();
        list.add(fgr1);
        list.add(fgr2);
        fgr1.setFeedId(1);
        fgr1.setContents("a");
        fgr2.setFeedId(2);
        fgr2.setContents("b");

        given(mapper.getFeed(p)).willReturn(list);

        List<String> picsA = new ArrayList();
        picsA.add("a1.jpg");
        picsA.add("a2.jpg");

        List<String> picsB = new ArrayList();
        picsB.add("b1.jpg");
        picsB.add("b2.jpg");
        picsB.add("b3.jpg");

        given(mapper.getFeedPicsByFeedId(fgr1.getFeedId())).willReturn(picsA);
        given(mapper.getFeedPicsByFeedId(fgr2.getFeedId())).willReturn(picsB);

        List<FeedCommentGetRes> commentsA = new ArrayList();
        FeedCommentGetRes fcgrA1 = new FeedCommentGetRes();
        FeedCommentGetRes fcgrA2 = new FeedCommentGetRes();
        commentsA.add(fcgrA1);
        commentsA.add(fcgrA2);
        fcgrA1.setComment("a1");
        fcgrA2.setComment("a2");

        List<FeedCommentGetRes> commentsB = new ArrayList();
        FeedCommentGetRes fcgrB1 = new FeedCommentGetRes();
        FeedCommentGetRes fcgrB2 = new FeedCommentGetRes();
        FeedCommentGetRes fcgrB3 = new FeedCommentGetRes();
        FeedCommentGetRes fcgrB4 = new FeedCommentGetRes();
        commentsB.add(fcgrB1);
        commentsB.add(fcgrB2);
        commentsB.add(fcgrB3);
        commentsB.add(fcgrB4);
        fcgrB1.setComment("b1");
        fcgrB2.setComment("b2");
        fcgrB3.setComment("b3");
        fcgrB4.setComment("b4");

        given(mapper.getFeedComment(fgr1.getFeedId())).willReturn(commentsA);
        given(mapper.getFeedComment(fgr2.getFeedId())).willReturn(commentsB);

        List<FeedGetRes> res = service.getFeed(p);
        verify(mapper).getFeed(p);

        assertEquals(list.size(), res.size(), "리턴값이 다름");
        for(FeedGetRes item: list){
            verify(mapper).getFeedPicsByFeedId(item.getFeedId());
            verify(mapper).getFeedComment(item.getFeedId());
        }

        FeedGetRes actualItemA = res.get(0);
        assertEquals(picsA, actualItemA.getPics(), "fgr1의 이미지 다름");
        assertEquals(commentsA, actualItemA.getComments(), "fgr1의 댓글 다름");
        assertEquals(0, actualItemA.getIsMoreComment());


        FeedGetRes actualItemB = res.get(1);
        assertEquals(picsB, actualItemB.getPics(), "fgr2의 이미지 다름");
        assertEquals(commentsB, actualItemB.getComments(), "fgr2의 댓글 다름");
        assertEquals(3, commentsB.size());
        assertEquals(1, actualItemB.getIsMoreComment());
    }
}