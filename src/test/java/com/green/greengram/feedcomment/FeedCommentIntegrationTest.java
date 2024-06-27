package com.green.greengram.feedcomment;

import com.green.greengram.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

public class FeedCommentIntegrationTest extends BaseIntegrationTest {
    private final String BASE_URL = "/api/feed/comment";

    @Test
    @DisplayName("댓글 쓰기")
    @Rollback(false)
    public void postFeedCommentTest() {

    }

}

