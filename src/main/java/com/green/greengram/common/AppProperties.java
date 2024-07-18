package com.green.greengram.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Getter
//@ConfigurationProperties: yaml에 작성되어 있는 데이터를 객체화 시켜주는 애노테이션
@ConfigurationProperties(prefix = "app") //prefix의 'app'은 applicationl.yaml파일의 44Line의 'app'을 의미
@RequiredArgsConstructor
public class AppProperties {
   private final Jwt jwt;
   private final Oauth2 oauth2;

    //class명 Jwt는 application.yaml의 35Line의 'jwt'를 의미
    //멤버 필드명은 application.yaml의 app/jwt/* 속성명과 매칭
    //application.yaml에서 '-'는 멤버필드에서 카멜케이스기법과 매칭함
    @Getter
    public static class Jwt {
        private final String secret;
        private final String headerSchemaName;
        private final String tokenType;
        private final long accessTokenExpiry;
        private final long refreshTokenExpiry;
        private final String refreshTokenCookieName;
        private final int refreshTokenCookieMaxAge;

        //immutable 객체를 만들기 위함
        @ConstructorBinding
        public Jwt(String secret, String headerSchemaName, String tokenType, long accessTokenExpiry, long refreshTokenExpiry, String refreshTokenCookieName) {
            this.secret = secret;
            this.headerSchemaName = headerSchemaName;
            this.tokenType = tokenType;
            this.accessTokenExpiry = accessTokenExpiry;
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieName = refreshTokenCookieName;
            this.refreshTokenCookieMaxAge = (int)(refreshTokenExpiry * 0.001); // ms > s 변환
        }
    }

    @Getter
    public static class Oauth2 {
        private final String baseUri;
        private final String authorizationRequestCookieName;
        private final String redirectUriParamCookieName;
        private final int cookieExpirySeconds;
        private final List<String> authorizedRedirectUris;

        @ConstructorBinding
        public Oauth2(String baseUri, String authorizationRequestCookieName, String redirectUriParamCookieName, int cookieExpirySeconds, List<String> authorizedRedirectUris) {
            this.baseUri = baseUri;
            this.authorizationRequestCookieName = authorizationRequestCookieName;
            this.redirectUriParamCookieName = redirectUriParamCookieName;
            this.cookieExpirySeconds = cookieExpirySeconds;
            this.authorizedRedirectUris = authorizedRedirectUris;
        }
    }
}
