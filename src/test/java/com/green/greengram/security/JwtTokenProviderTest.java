package com.green.greengram.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

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