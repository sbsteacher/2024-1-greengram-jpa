package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedFavoriteMapper {
    int insFeedFavorite(FeedFavoriteReq p);
    int delFeedFavorite(FeedFavoriteReq p);


}
