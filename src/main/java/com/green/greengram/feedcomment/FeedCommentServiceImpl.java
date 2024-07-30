package com.green.greengram.feedcomment;

import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import com.green.greengram.exception.CustomException;
import com.green.greengram.exception.MemberErrorCode;
import com.green.greengram.feed.FeedRepository;
import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentGetResInterface;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.security.AuthenticationFacade;
import com.green.greengram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentServiceImpl implements FeedCommentService {
    private final FeedCommentMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    private final FeedCommentRepository repository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    @Override
    public long postFeedComment(FeedCommentPostReq p) {
        //Feed Entity(영속성)
        Feed feed = feedRepository.getReferenceById(p.getFeedId());

        //User Entity(영속성)
        User user = userRepository.getReferenceById(authenticationFacade.getLoginUserId());

        //Feed Comment Entity (비영속성)
        FeedComment fc = new FeedComment();
        fc.setFeed(feed);
        fc.setUser(user);
        fc.setComment(p.getComment());

        FeedComment fc2 = repository.save(fc);
        log.info("fc equals fc2 : {}", fc == fc2);
        return fc.getFeedCommentId();
    }

    @Override
    public int delFeedComment(FeedCommentDeleteReq p){
        FeedComment fc = repository.getReferenceById(p.getFeedCommentId());
        //fc.getUser().getUserId()를 그래프 탐색이라 호칭
        if(fc.getUser().getUserId() != authenticationFacade.getLoginUserId()) {
            throw new CustomException(MemberErrorCode.UNAUTHORIZED);
        }
        repository.delete(fc);
        return 1;
    }

    @Override
    public List<FeedCommentGetResInterface> feedCommentListGet(long feedId) {
        return repository.findAllByFeedCommentLimit3AndInfinity(feedId);
    }
}
