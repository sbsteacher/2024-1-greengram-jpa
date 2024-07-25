package com.green.greengram.userfollow.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@EqualsAndHashCode
public class UserFollowPostReq {
    //필드 주입 (setter, 생성자가 아닌 reflection API를 사용하여 접근제어자(private)을 무력화 시켜서 주소값을 주입)
    @Schema(example = "2", description = "팔로잉 유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private long toUserId;
}
