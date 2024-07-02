package com.green.greengram.security.oauth2;

import com.green.greengram.common.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFilureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final OAuth2AuthenticationRequestBasedOnCookieRepository repository;
}
