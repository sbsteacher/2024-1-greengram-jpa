package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedfavorite.model.FeedFavoriteReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedFavoriteMapperTest {

    @Autowired
    private FeedFavoriteMapper mapper;

    @Test
    void insFeedFavorite() {

    }

    @Test
    void selFeedFavoriteForTestMe() {

    }

    @Test
    void selFeedFavoriteForTestYou() {
        FeedFavoriteReq entireParam = new FeedFavoriteReq(5, 1);
        List<FeedFavoriteEntity> entireList = mapper.selFeedFavoriteForTest(entireParam);

        assertEquals(10, entireList.size(), "전체 레코드 가져오는 부분");

        FeedFavoriteEntity res0 = new FeedFavoriteEntity();

        res0.setCreatedAt("2024-05-22 11:50:59");
        assertEquals(res0, entireList.get(0), "0번 레코드");

        FeedFavoriteEntity res3 = new FeedFavoriteEntity();
        res3.setFeedId(8);
        res3.setUserId(4);
        res3.setCreatedAt("2024-05-09 11:39:31");
        assertEquals(res3, entireList.get(3), "3번 레코드");



    }

    @Test
    void delFeedFavorite() {

    }
}