package com.green.greengram.security;

import com.green.greengram.security.jwt.JwtAuthenticationAccessDeniedHandler;
import com.green.greengram.security.jwt.JwtAuthenticationEntryPoint;
import com.green.greengram.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    //@Component로 빈등록을 하였기 때문에 DI가 된다.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
      메소드 빈등록으로 주로쓰는 케이스는 (현재 기준으로 설명하면) Security와 관련된
      빈등록을 여러개 하고 싶을 때 메소드 형식으로 빈등록 하면 한 곳에 모을 수가 있으니 좋다.
        메소드 빈등록으로 하지 않으면 각각 클래스로 만들어야 한다.

     */

    @Bean //메소드 타입의 빈 등록 (파라미터, 리턴타입 중요) 파라미터는 빈등록할때 필요한 객체
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //파라미터없이 내가 직접 new 객체화해서 리턴으로 빈등록 가능

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
                                        //회원가입, 로그인 인증이 안 되어 있더라도 사용 가능하게 세팅
                                         "/api/user/sign-up"
                                        ,"/api/user/sign-in"
                                        ,"/api/user/access-token"

                                        //swagger 사용할 수 있게 세팅
                                        , "/swagger"
                                        , "/swagger-ui/**"
                                        , "/v3/api-docs/**"

                                        //사진
                                        , "/pic/**"
                                        , "/fimg/**"

                                        //프론트 화면 보일수 있게 세팅
                                        ,"/"
                                        ,"/index.html"
                                        , "/css/**"
                                        , "/js/**"
                                        , "/static/**"

                                        //프론트에서 사용하는 라우터 주소
                                        , "/sign-in"
                                        , "/sign-up"
                                        , "/profile/*"
                                        , "/feed"

                                ).permitAll()
                            .anyRequest().authenticated() //로그인이 되어 있어야만 허용
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                                                         .accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler())
                )

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












