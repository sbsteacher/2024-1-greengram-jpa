package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SignInPostReq {
    @NotBlank(message = "아이디를 확인해 주세요.")
    @Schema(example = "mic", description = "유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;

    @NotBlank(message = "비밀번호를 확인해 주세요.")
    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(example = "1212", description = "유저 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;

    @JsonIgnore
    private String providerType;
}
