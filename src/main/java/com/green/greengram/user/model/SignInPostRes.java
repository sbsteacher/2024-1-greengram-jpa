package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInPostRes {
    @Schema(example = "1", description = "유저PK")
    private long userId;
    @Schema(example = "홍길동", description = "유저 이름")
    private String nm;
    @Schema(example = "c450808c-48cc-4191-af4a-92c11be40cf4.jpg", description = "유저 프로필 이미지")
    private String pic;
}
