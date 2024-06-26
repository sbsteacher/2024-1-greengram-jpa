package com.green.greengram.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Getter
//@ConfigurationProperties: yaml에 작성되어 있는 데이터를 객체화 시켜주는 애노테이션
@ConfigurationProperties(prefix = "app") //prefix의 'app'은 applicationl.yaml파일의 44Line의 'app'을 의미
public class AppProperties {
    private final Jwt jwt = new Jwt();
    //class명 Jwt는 application.yaml의 35Line의 'jwt'를 의미
    //멤버 필드명은 application.yaml의 app/jwt/* 속성명과 매칭
    //application.yaml에서 '-'는 멤버필드에서 카멜케이스기법과 매칭함
    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private String headerSchemaName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private int refreshTokenCookieMaxAge;

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = (int)(refreshTokenExpiry * 0.001); // ms > s 변환
        }
    }
}
