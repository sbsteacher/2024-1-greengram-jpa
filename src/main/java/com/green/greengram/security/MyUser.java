package com.green.greengram.security;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUser {
    private long userId; //로그인한 사용자의 pk값
    private String role; //사용자 권한, ROLE_권한이름
}
