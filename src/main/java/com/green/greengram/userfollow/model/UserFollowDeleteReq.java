package com.green.greengram.userfollow.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
@EqualsAndHashCode
public class UserFollowDeleteReq {
    @Schema(name="to_user_id", example = "2", description = "팔로잉 유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private long toUserId;

    @ConstructorProperties({ "to_user_id" })
    public UserFollowDeleteReq(long toUserId) {
        this.toUserId = toUserId;
    }
}
