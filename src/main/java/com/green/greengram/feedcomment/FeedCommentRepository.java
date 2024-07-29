package com.green.greengram.feedcomment;

import com.green.greengram.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    //@Query(value = "INSERT INTO feed_comment (feed_id, user_id, comment, created_at, updated_at) VALUES (:feedId, :userId, :comment, now(), now())", nativeQuery = true)
    //FeedComment saveFeedComment(Long feedId, Long userId, String comment);
}
