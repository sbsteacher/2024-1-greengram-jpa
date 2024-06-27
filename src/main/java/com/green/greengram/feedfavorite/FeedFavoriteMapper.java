package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedfavorite.model.FeedFavoriteReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedFavoriteMapper {
    int insFeedFavorite(FeedFavoriteReq p);
    List<FeedFavoriteEntity> selFeedFavoriteForTest(FeedFavoriteReq p);
    int delFeedFavorite(FeedFavoriteReq p);
}
