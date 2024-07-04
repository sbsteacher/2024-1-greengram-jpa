package com.green.greengram.security.oauth2;

import com.green.greengram.common.AppProperties;
import com.green.greengram.common.CookieUtils;
import com.green.greengram.security.MyUser;
import com.green.greengram.security.MyUserDetails;
import com.green.greengram.security.MyUserOAuth2Vo;
import com.green.greengram.security.jwt.JwtTokenProviderV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2AuthenticationRequestBasedOnCookieRepository repository;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("targetUrl: {}", targetUrl);
        if(response.isCommitted()) { //응답 객체가 만료된 경우(다른곳에서 응답처리를 한 경우)
            log.error("onAuthenticationSuccess - 응답이 만료됨");
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String redirectUri = cookieUtils.getCookie(request
                                                 , appProperties.getOauth2().getRedirectUriParamCookieName()
                                                 , String.class);

        // (yaml) app.oauth2.uthorized-redirect-uris 리스트에 없는 Uri인 경우
        if(redirectUri != null && !hasAuthorizedRedirectUri(redirectUri)) {
            throw new IllegalArgumentException("인증되지 않은 Redirect URI입니다.");
        }

        //FE가 원하는 redirect_url값이 저장
        String targetUrl = redirectUri == null ? getDefaultTargetUrl() : redirectUri;

        //user_id, nm, pic, access_token를 FE에게 리턴
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        MyUserOAuth2Vo myUserOAuth2Vo = (MyUserOAuth2Vo)myUserDetails.getMyUser();
        MyUser myUser = MyUser.builder()
                .userId(myUserOAuth2Vo.getUserId())
                .role(myUserOAuth2Vo.getRole())
                .build();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

        //refreshToken은 보안 쿠키를 이용해서 처리(FE가 따로 작업을 하지 않아도 아래 cookie값은 항상 넘어온다.)
        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(response, appProperties.getJwt().getRefreshTokenCookieName());
        cookieUtils.setCookie(response
                            , appProperties.getJwt().getRefreshTokenCookieName()
                            , refreshToken
                            , refreshTokenMaxAge);

        //http://localhost:8080/oauth/redirect?user_id=1&nm=홍길동&pic=https://image.jpg&access_token=aslkdjslajf
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("user_id", myUserOAuth2Vo.getUserId())
                .queryParam("nm", myUserOAuth2Vo.getNm())
                .queryParam("pic", myUserOAuth2Vo.getPic())
                .queryParam("access_token", accessToken)
                .build()
                .toUriString();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        repository.removeAuthorizationRequestCookies(response);
    }

    //우리가 설정한 redirect-uri인지 체크
    private boolean hasAuthorizedRedirectUri(String uri) {
        URI savedCookieRedirectUri = URI.create(uri);
        log.info("savedCookieRedirectUri.getHost(): {}", savedCookieRedirectUri.getHost());
        log.info("savedCookieRedirectUri.getPort(): {}", savedCookieRedirectUri.getPort());

        for(String redirectUri : appProperties.getOauth2().getAuthorizedRedirectUris()) {
            URI authorizedUri = URI.create(redirectUri);
            if(savedCookieRedirectUri.getHost().equalsIgnoreCase(authorizedUri.getHost())
                    && savedCookieRedirectUri.getPort() == authorizedUri.getPort()) {
                return true;
            }
        }
        return false;
    }
}
