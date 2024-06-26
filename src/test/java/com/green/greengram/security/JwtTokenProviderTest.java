package com.green.greengram.security;

import com.green.greengram.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void test() {
        UserDetails userDetails = MyUserDetails.builder()
                .role("ddd")
                .userId(10)
                .build();
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        Claims claims = jwtTokenProvider.getAllClaims(refreshToken);

        System.out.println(claims.get("signedUser"));
    }

}