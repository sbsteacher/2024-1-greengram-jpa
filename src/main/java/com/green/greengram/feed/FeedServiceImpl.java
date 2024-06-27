package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.feed.model.*;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.green.greengram.common.GlobalConst.COMMENT_SIZE_PER_FEED;

@RequiredArgsConstructor
@Service
@Slf4j
public class FeedServiceImpl implements FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p){
        p.setUserId(authenticationFacade.getLoginUserId());
        mapper.postFeed(p);
        if(pics == null){
            return FeedPostRes.builder()
                    .feedId(p.getFeedId())
                    .build();
        }
        FeedPicPostDto dto = FeedPicPostDto.builder()
                            .feedId(p.getFeedId())
                            .build();
        try {
            String path = String.format("feed/%d", p.getFeedId());
            customFileUtils.makeFolders(path);
            for(MultipartFile pic : pics){
                String fileName = customFileUtils.makeRandomFileName(pic);
                String target = String.format("%s/%s" , path, fileName);
                customFileUtils.transferTo(pic, target);
                dto.getFileNames().add(fileName);
            }
            mapper.postFeedPics(dto);

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Feed등록 오류");
        }

        return FeedPostRes.builder()
                .feedId(dto.getFeedId())
                .pics(dto.getFileNames())
                .build();
    }

    public List<FeedGetRes> getFeed(FeedGetReq p){
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        List<FeedGetRes> list = mapper.getFeed(p);

        for(FeedGetRes res : list){
            List<String> pics = mapper.getFeedPicsByFeedId(res.getFeedId());
            res.setPics(pics);

            List<FeedCommentGetRes> comments = mapper.getFeedComment(res.getFeedId());
            if(comments.size()==COMMENT_SIZE_PER_FEED){
                res.setIsMoreComment(1);
                comments.remove(COMMENT_SIZE_PER_FEED - 1);
            }
            res.setComments(comments);
        }
        return list;
    }
}
