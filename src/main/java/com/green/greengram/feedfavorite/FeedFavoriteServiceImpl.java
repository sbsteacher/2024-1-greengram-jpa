package com.green.greengram.feedfavorite;

import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedFavorite;
import com.green.greengram.feedfavorite.model.FeedFavoriteReq;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedFavoriteServiceImpl implements FeedFavoriteService {
    private final FeedFavoriteMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    private final FeedFavoriteRepository repository;

    public int toggleReq(FeedFavoriteReq p) {
        FeedFavorite feedFavorite = repository.findFeedFavoriteByFeedIdAndSignedUserId(p.getFeedId(), authenticationFacade.getLoginUserId());
        if(feedFavorite == null) {
           repository.saveFeedFavorite(p.getFeedId(), authenticationFacade.getLoginUserId());
           return 1;
        }
        repository.delete(feedFavorite);
        return 0;
    }

}
