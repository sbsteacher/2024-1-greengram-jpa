package com.green.greengram.security;

import lombok.Getter;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyUserOAuth2Vo extends MyUser {
    private final String nm;
    private final String pic;

    public MyUserOAuth2Vo(long userId, List<String> roles, String nm, String pic) {
        super(userId, roles);
        this.nm = nm;
        this.pic = pic;
    }

}

