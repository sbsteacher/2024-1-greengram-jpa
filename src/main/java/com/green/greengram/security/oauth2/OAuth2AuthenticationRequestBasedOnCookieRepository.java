package com.green.greengram.security.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.AppProperties;
import com.green.greengram.common.CookieUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationRequestBasedOnCookieRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final CookieUtils cookieUtils;
    private final AppProperties appProperties;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        log.info("CookieRepository - loadAuthorizationRequest");
        return cookieUtils.getCookie(request
                                    , appProperties.getOauth2().getAuthorizationRequestCookieName()
                                    , OAuth2AuthorizationRequest.class
        );
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("CookieRepository - saveAuthorizationRequest");
        if(authorizationRequest == null) { //
            this.removeAuthorizationRequestCookies(response);
            return;
        }
        cookieUtils.setCookie(response
                , appProperties.getOauth2().getRedirectUriParamCookieName()
                , authorizationRequest
                , appProperties.getOauth2().getCookieExpirySeconds());

        String redirectUriAfterLogin = request.getParameter(appProperties.getOauth2().getRedirectUriParamCookieName());
        log.info("redirectUriAfterLogin: {}", redirectUriAfterLogin);
        if(StringUtils.isNotBlank(redirectUriAfterLogin)) {
            cookieUtils.setCookie(response
                    , appProperties.getOauth2().getRedirectUriParamCookieName()
                    , redirectUriAfterLogin
                    , appProperties.getOauth2().getCookieExpirySeconds()
            );
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("CookieRepository - removeAuthorizationRequest");
        return this.loadAuthorizationRequest(request);
    }

    private void removeAuthorizationRequestCookies(HttpServletResponse response) {
        log.info("CookieRepository - removeAuthorizationRequestCookies");
        cookieUtils.deleteCookie(response, appProperties.getOauth2().getAuthorizationRequestCookieName());
        cookieUtils.deleteCookie(response, appProperties.getOauth2().getRedirectUriParamCookieName());
    }
}
