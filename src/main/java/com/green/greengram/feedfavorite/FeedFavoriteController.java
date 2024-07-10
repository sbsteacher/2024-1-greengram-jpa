package com.green.greengram.feedfavorite;

import com.green.greengram.common.model.MyResponse;
import com.green.greengram.feedfavorite.model.FeedFavoriteReq;

public interface FeedFavoriteController {
    MyResponse<Integer> toggleReq(FeedFavoriteReq p);
}
