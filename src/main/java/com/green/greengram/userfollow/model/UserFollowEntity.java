package com.green.greengram.userfollow.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class UserFollowEntity {
    private long fromUserId;
    private long toUserId;
    private String createdAt;
}
