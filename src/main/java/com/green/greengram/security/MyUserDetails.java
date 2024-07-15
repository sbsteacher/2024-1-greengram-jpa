package com.green.greengram.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;


@NoArgsConstructor
@Setter
@Getter
//시큐리티에서 로그인 처리를 할 때 사용하는 객체
public class MyUserDetails implements UserDetails, OAuth2User {

    private MyUser myUser; //JWT 만들 때 payload에 담을 데이터를 담은 객체

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 단수 > 복수로 변경
        // (1)
        // return Collections.singletonList(new SimpleGrantedAuthority(myUser.getRoles()));

        // (2)
        // List<GrantedAuthority> list2 = new ArrayList();
        // list2.add(new SimpleGrantedAuthority("ROLE_USER"));
        // return list2;

        // (1), (2)는 동일한 결과

        // List<String>  >>   List<GrantedAuthority> 변경하는 작업
        List<GrantedAuthority> list = new ArrayList();
        for(String role : myUser.getRoles()) {
            list.add(new SimpleGrantedAuthority(role));
        }
        return list;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return myUser == null ? "GUEST" : String.valueOf(myUser.getUserId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
