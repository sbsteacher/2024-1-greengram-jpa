package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserProfilePatchReq {
    private long signedUserId;
    private MultipartFile pic;

    @JsonIgnore
    private String picName;
}
