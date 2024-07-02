package com.green.greengram.security.oauth2;

import com.green.greengram.security.MyUser;
import com.green.greengram.security.MyUserDetails;
import com.green.greengram.security.SignInProviderType;
import com.green.greengram.security.oauth2.userinfo.OAuth2UserInfo;
import com.green.greengram.security.oauth2.userinfo.OAuth2UserInfoFactory;
import com.green.greengram.user.UserMapper;
import com.green.greengram.user.model.SignInPostReq;
import com.green.greengram.user.model.SignUpPostReq;
import com.green.greengram.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            return this.process(userRequest);
        } catch (Exception e) {
            throw  new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        //각 소셜플랫폼에 맞는 enum타입을 얻는다.
        SignInProviderType signInProviderType = SignInProviderType.valueOf(userRequest.getClientRegistration()
                                                                                      .getRegistrationId()
                                                                                      .toUpperCase()
        );

        //규격화된 UserInfo객체로 변환
        OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoFactory.getOAuth2UserInfo(signInProviderType, oAuth2User.getAttributes());
        SignInPostReq signInParam = new SignInPostReq();
        signInParam.setUid(oAuth2UserInfo.getId()); //플랫폼에서 넘어오는 유니크값(항상 같은 값이며 다른 사용자와 구별되는 유니크 값)
        signInParam.setProviderType(signInProviderType.name());
        User user = mapper.signInPost(signInParam);

        MyUser myUser = new MyUser();
        myUser.setRole("ROLE_USER");

        if(user == null) { //회원가입 처리
            SignUpPostReq signUpParam = new SignUpPostReq();
            signUpParam.setUid(oAuth2UserInfo.getId());
            signUpParam.setNm(oAuth2User.getName());
            signUpParam.setPic(oAuth2UserInfo.getProfilePicUrl());
            int result = mapper.signUpPostReq(signUpParam);
            myUser.setUserId(signUpParam.getUserId()); //회원가입 후 유저 pk값 담기
        } else {
            myUser.setUserId(user.getUserId()); //이미 회원가입 된 유저 pk값 담기
        }

        MyUserDetails signInUser = new MyUserDetails();
        signInUser.setMyUser(myUser);
        return signInUser;
    }
}
