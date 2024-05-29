package com.green.greengram.feed.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class FeedPicPostDto {
    private long feedId; //10
    @Builder.Default
    private List<String> fileNames = new ArrayList();
    //"a.jpg", "b.jpg", "c.jpg"
}
/*
        INSERT INTO feed_pics
        ( feed_id, pic )
        VALUES
        ( 10, 'a.jpg' )
        , ( 10, 'b.jpg' )
        , ( 10, 'c.jpg' )


        <foreach item="item" collection="fileNames"
                 open="(" separator="),(" close=")">
            #{feedId}, #{item}
        </foreach>

        INSERT INTO feed_pics
        ( feed_id, pic )
        VALUES
        ( 10, 'a.jpg'
        ),( 10, 'b.jpg'
        ),( 10, 'c.jpg' )

 */













