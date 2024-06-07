package com.green.greengram.feedfavorite;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedfavorite.model.FeedFavoriteReq;

public interface FeedFavoriteController {
    ResultDto<Integer> toggleReq(FeedFavoriteReq p);
}
