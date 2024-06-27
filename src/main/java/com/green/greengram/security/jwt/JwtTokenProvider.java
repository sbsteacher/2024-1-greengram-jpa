package com.green.greengram.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.AppProperties;
import com.green.greengram.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final ObjectMapper om;
    private final AppProperties appProperties;
    private SecretKey secretKey;

    @PostConstruct //생성자 호출 이후에 한번 실행하는 메소드
    public void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.getJwt().getSecret()));
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, appProperties.getJwt().getAccessTokenExpiry());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, appProperties.getJwt().getRefreshTokenExpiry());
    }



    private String generateToken(UserDetails userDetails, long tokenValidMilliSecond) {
        return Jwts.builder()
                .claims(createClaims(userDetails))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMilliSecond))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public Claims createClaims(UserDetails userDetails) {
        try {
            String json = om.writeValueAsString(userDetails);
            return Jwts.claims().add("signedUser", json).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            String json = (String)claims.get("signedUser");
            return om.readValue(json, MyUserDetails.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
