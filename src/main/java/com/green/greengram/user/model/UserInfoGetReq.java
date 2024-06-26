package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class UserInfoGetReq {
    //@Schema(name = "signed_user_id", defaultValue = "15", description = "로그인한 사용자 PK")
    @JsonIgnore
    private long signedUserId;

    @Schema(name = "profile_user_id", defaultValue = "17", description = "프로필 사용자 PK")
    private Long profileUserId;

    @ConstructorProperties({ "profile_user_id" })
    public UserInfoGetReq(long profileUserId) {
        this.profileUserId = profileUserId;
    }
}
