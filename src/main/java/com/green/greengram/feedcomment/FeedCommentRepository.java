package com.green.greengram.feedcomment;

import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentGetResInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    //@Query(value = "INSERT INTO feed_comment (feed_id, user_id, comment, created_at, updated_at) VALUES (:feedId, :userId, :comment, now(), now())", nativeQuery = true)
    //FeedComment saveFeedComment(Long feedId, Long userId, String comment);

    @Query(value = " SELECT fc.feed_comment_id as feedCommentId, fc.comment, fc.created_at as createdAt" +
            " , u.user_id as writerId, u.nm as writerNm, u.pic as writerPic  " +
            " FROM feed_comment fc " +
            " INNER JOIN feed f ON fc.feed_id = f.feed_id " +
            " INNER JOIN user u ON fc.user_id = u.user_id " +
            " WHERE f.feed_id = :feedId " +
            " ORDER BY fc.feed_comment_id ASC" +
            " LIMIT 3, 9999 ", nativeQuery = true)
    List<FeedCommentGetResInterface> findAllByFeedCommentLimit3AndInfinity(Long feedId);
}
