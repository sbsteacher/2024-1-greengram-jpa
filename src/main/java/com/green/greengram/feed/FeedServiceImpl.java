package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedPic;
import com.green.greengram.entity.User;
import com.green.greengram.feed.model.*;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.security.AuthenticationFacade;
import com.green.greengram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.green.greengram.common.GlobalConst.COMMENT_SIZE_PER_FEED;

@RequiredArgsConstructor
@Service
@Slf4j
public class FeedServiceImpl implements FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final AuthenticationFacade authenticationFacade;
    private final FeedRepository repository;
    private final FeedPicRepository feedPicRepository;
    private final UserRepository userRepository;


    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        User signedUser = userRepository.getReferenceById(authenticationFacade.getLoginUserId());

        Feed feed = new Feed();
        feed.setUser(signedUser);
        feed.setContents(p.getContents());
        feed.setLocation(p.getLocation());
        repository.save(feed);

        if(pics == null){
            return FeedPostRes.builder()
                    .feedId(feed.getFeedId())
                    .build();
        }

        List<String> picList = new ArrayList();

        try {
            List<FeedPic> feedPicList = new ArrayList();
            String path = String.format("feed/%d", feed.getFeedId());
            customFileUtils.makeFolders(path);
            for(MultipartFile pic : pics){
                String fileName = customFileUtils.makeRandomFileName(pic);
                String target = String.format("%s/%s" , path, fileName);
                customFileUtils.transferTo(pic, target);

                picList.add(fileName);

                FeedPic feedPic = new FeedPic();
                feedPicList.add(feedPic);

                feedPic.setFeed(feed);
                feedPic.setPic(fileName);
            }
            feedPicRepository.saveAll(feedPicList);
            /*
            JPA Batch Insert(대량 insert)는 쓰기 지연을 사용해 동작.
            이는 DB에 insert 후 id가 할당되는 identity 전략을 사용하면 사용할 수 없다는 의미
            yaml 파일에 " # JPA Batch 작업(Insert, Update) " 주석인 부분과
            DB URL에 파라미터값 추가 " rewriteBatchedStatements=true "
             */

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Feed등록 오류");
        }

        return FeedPostRes.builder()
                .feedId(feed.getFeedId())
                .pics(picList)
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
