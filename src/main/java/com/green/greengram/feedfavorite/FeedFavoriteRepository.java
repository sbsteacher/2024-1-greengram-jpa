package com.green.greengram.feedfavorite;

import com.green.greengram.entity.FeedFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedFavoriteRepository extends JpaRepository<FeedFavorite, Long> {
    @Query(" select ff from FeedFavorite ff WHERE ff.feed.feedId = :feedId AND ff.user.userId = :userId ")
    FeedFavorite findFeedFavoriteByFeedIdAndSignedUserId(Long feedId, Long userId);

    @Query(value = "insert into feed_favorite ( feed_id, user_id, created_at ) VALUES ( :feedId, :userId, now() ) "
            , nativeQuery = true)
    void saveFeedFavorite(Long feedId, Long userId);
}
