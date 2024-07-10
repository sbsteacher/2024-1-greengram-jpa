package com.green.greengram.security;

import com.green.greengram.security.jwt.JwtAuthenticationAccessDeniedHandler;
import com.green.greengram.security.jwt.JwtAuthenticationEntryPoint;
import com.green.greengram.security.jwt.JwtAuthenticationFilter;
import com.green.greengram.security.oauth2.MyOAuth2UserService;
import com.green.greengram.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.green.greengram.security.oauth2.OAuth2AuthenticationRequestBasedOnCookieRepository;
import com.green.greengram.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    //@Component로 빈등록을 하였기 때문에 DI가 된다.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationRequestBasedOnCookieRepository repository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final MyOAuth2UserService myOAuth2UserService;

    /*
      메소드 빈등록으로 주로쓰는 케이스는 (현재 기준으로 설명하면) Security와 관련된
      빈등록을 여러개 하고 싶을 때 메소드 형식으로 빈등록 하면 한 곳에 모을 수가 있으니 좋다.
        메소드 빈등록으로 하지 않으면 각각 클래스로 만들어야 한다.
     */

    @Bean //메소드 타입의 빈 등록 (파라미터, 리턴타입 중요) 파라미터는 빈등록할때 필요한 객체
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //파라미터없이 내가 직접 new 객체화해서 리턴으로 빈등록 가능

        CommonOAuth2Provider a;

        return httpSecurity.sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) //시큐리티에서 세션 사용을 하지 않음을 세팅
                .httpBasic(http -> http.disable())
                // (SSR 서버사이드 렌더링 하지 않는다. 즉 html화면을 백엔드가 만들지 않는다.)
                // 백엔드에서 화면을 만들지 않더라도 위 세팅을 끄지 않아도 괜찮다. 사용하지 않는 걸 끔으로써 리소스 확보
                // 하기 위해서 사용하는 개념
                // 정리하면, 시큐리티에서 제공해주는 로그인 화면 사용하지 않겠다.
                .formLogin(form -> form.disable()) //form 로그인 방식을 사용하지 않음을 세팅
                .csrf(csrf -> csrf.disable()) //CSRF (CORS랑 많이 헷갈려 함)
                //requestMatchers
                .authorizeHttpRequests(auth ->
                    auth.requestMatchers(
                              "/api/feed"
                            , "/api/feed/*"
                            , "/api/user/pic"
                            , "/api/user/follow"
                    )
                    .authenticated()
                    .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                                                         .accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler())
                )
                .oauth2Login( oauth2 -> oauth2.authorizationEndpoint(
                                            auth -> auth.baseUri("/oauth2/authorization")
                                                        .authorizationRequestRepository(repository)

                                        )
                        .redirectionEndpoint( redirection -> redirection.baseUri("/*/oauth2/code/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(myOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                /*
                OAuth2 처리 순서

                OAuth2LoginAuthenticationFilter, OAuth2AuthorizationRequestRedirectFilter

                FE > registration 선택
                   > http://localhost:8080/oauth2/authorization/${registrationId}?redirect_uri=${redirectUrl} 백엔드 요청
                     (registrationId: 플랫폼 이름,  redirectUrl: 소셜 로그인 완료 후 FE로 보내야하는 주소값
                   > (#) registration에 요청을 보낼 정보를 정리해서 요청 객체(OAuth2AuthorizationRequest) 생성
                   > OAuth2AuthenticationRequestBasedOnCookieRepository - saveAuthorizationRequest 메소드 호출
                     (쿠키에 요청 정보, FE Redirect URL 저장 > 쿠키사용은 플랫폼과 통신을 여러번 하는동안 데이터 유지에 사용)
                   > OAuth2AuthenticationRequestBasedOnCookieRepository - removeAuthorizationRequest 메소드 호출
                   > OAuth2AuthenticationRequestBasedOnCookieRepository - loadAuthorizationRequest 메소드 호출
                   (Access Token 받음)
                   > OAuth2UserService - loadUser

                   ( 사용자 정보 받았다 / 못 받았다 분기)
                   받았다 > OAuth2AuthenticationSuccessHandler - onAuthenticationSuccess 호출
                   못 받았다 >
                 */




                /*
                //만약, permitAll메소드가 void였다면 아래와 같이 작성을 해야함
                .authorizeHttpRequests(auth -> {
                    //
                    auth.requestMatchers("/api/user/sign-up","/api/user/sign-in").permitAll();
                    auth.requestMatchers("/api/ddd").authenticated();
                })
                //permitAll 메소드가 자기 자신의 주소값을 리턴한다면 체이닝 기법 가능
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/user/sign-up","/api/user/sign-in").permitAll()
                        .requestMatchers("/api/ddd").authenticated();
                })
                */


                .build();


/*
        //위 람다식을 풀어쓰면 아래와 같다. 람다식은 짧게 적을 수 있는 기법.
        return httpSecurity.sessionManagement(new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
            @Override
            public void customize(SessionManagementConfigurer<HttpSecurity> session) {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            }
        }).build();
*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}












