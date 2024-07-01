package com.green.greengram.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
로그인한 사용자 정보를 가져오는 객체를 만듬
Security Context Holder > Context > Authentication에서 getPrincipal 우리가 전에 넣었던 정보(MyUserDetails)를 얻어와서 처리한다.
Authentication이 해당 위치에 저장이 되어있어야만 스프링 시큐리티가 로그인으로 본다.
 */

@Component
public class AuthenticationFacade {

    public MyUser getLoginUser() {
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext()
                                                                          .getAuthentication()
                                                                          .getPrincipal();
        return myUserDetails == null ? null : myUserDetails.getMyUser();
    }

    public long getLoginUserId() {
        MyUser myUser = getLoginUser();
        return myUser == null ? 0 : myUser.getUserId();
    }
}
