package com.green.greengram.user;

import com.green.greengram.common.AppProperties;
import com.green.greengram.common.CookieUtils;
import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.security.AuthenticationFacade;
import com.green.greengram.security.SignInProviderType;
import com.green.greengram.security.jwt.JwtTokenProviderV2;
import com.green.greengram.security.MyUser;
import com.green.greengram.security.MyUserDetails;
import com.green.greengram.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final AppProperties appProperties;

    //SecurityContextHolder > Context > Authentication(UsernamePasswordAuthenticationToken) > MyUserDetails > MyUser

    public int signUpPostReq(MultipartFile pic, SignUpPostReq p){
        String saveFileName = customFileUtils.makeRandomFileName(pic);

        p.setPic(saveFileName);
        String password = passwordEncoder.encode(p.getUpw());
        //String password = BCrypt.hashpw(p.getUpw(),BCrypt.gensalt());
        p.setUpw(password);
        int result = mapper.signUpPostReq(p);
        if(pic == null){
            return result;
        }
        try{
            String path = String.format("user/%d", p.getUserId());
            customFileUtils.makeFolders(path);
            String target = String.format("%s/%s", path, saveFileName);
            customFileUtils.transferTo(pic, target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("실패");
        }
        return result;
    }

    public SignInPostRes signInPost(HttpServletResponse res, SignInPostReq p) {
        p.setProviderType(SignInProviderType.LOCAL.name());
        //p.setProviderType("LOCAL");
        User user = mapper.signInPost(p);

        if (user == null) {
            throw new RuntimeException("아이디를 확인해주세요.");
        }
        //if (!BCrypt.checkpw(p.getUpw(), user.getUpw())) {
        if (!passwordEncoder.matches(p.getUpw(), user.getUpw())) {
            throw new RuntimeException("비밀번호를 확인해주세요.");
        }

        MyUser myUser = MyUser.builder()
                .userId(user.getUserId())
                .role("ROLE_USER")
                .build();
        /*
        access, refresh token에 myUser(유저pk, 권한정보)를 담는다.
        refresh token에 myUser정보를 넣는 이유는 access token을 재발급 받을 때,
        access token에 myUser를 담기 위해서 담는다.
        왜 담았냐. FE가 accessToken을 계속 백엔드로 요청을 보낼 때, Header에 넣어서 보내준다.
        요청이 올 때마다 Request에 token이 담겨져 있는지 체크 (JwtAuthenticationFilter에서 한다.)
        token에 담겨져 있는 myUser를 빼내서 사용하기 위해 myUser를 담았다!
         */
        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

        //refreshToken은 보안 쿠키를 이용해서 처리(FE가 따로 작업을 하지 않아도 아래 cookie값은 항상 넘어온다.)
        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "refresh-token");
        cookieUtils.setCookie(res, "refresh-token", refreshToken, refreshTokenMaxAge);

        return SignInPostRes.builder()
                .userId(user.getUserId())
                .nm(user.getNm())
                .pic(user.getPic())
                .accessToken(accessToken)
                .build();
    }

    public Map<String, String> getAccessToken(HttpServletRequest req) {
        Cookie cookie = cookieUtils.getCookie(req, "refresh-token");
        if(cookie == null) { // refresh-token 값이 쿠키에 존재 여부
            throw new RuntimeException();
        }
        String refreshToken = cookie.getValue();
        if(!jwtTokenProvider.isValidateToken(refreshToken)) { //refresh-token 만료시간 체크
            throw new RuntimeException();
        }

        UserDetails auth = jwtTokenProvider.getUserDetailsFromToken(refreshToken);
        MyUser myUser = ((MyUserDetails)auth).getMyUser();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        Map<String, String> map = new HashMap();
        map.put("accessToken", accessToken);
        map.get("accessToken");
        return map;
    }

    public UserInfoGetRes getUserInfo(UserInfoGetReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        return mapper.selProfileUserInfo(p);
//        UserInfoGetRes res2 = new UserInfoGetRes();
//        res2.setNm("test2");
//        return res2;
    }


    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        String fileNm = customFileUtils.makeRandomFileName(p.getPic());
        log.info("saveFileName: {}", fileNm);
        p.setPicName(fileNm);
        mapper.updProfilePic(p);

        //기존 폴더 삭제
        try {
            //  D:/2024-01/download/greengram_tdd/user/600
            String midPath = String.format("user/%d", p.getSignedUserId());
            String delAbsoluteFolderPath = String.format("%s/%s", customFileUtils.uploadPath, midPath);
            customFileUtils.deleteFolder(delAbsoluteFolderPath);

            customFileUtils.makeFolders(midPath);
            String filePath = String.format("%s/%s", midPath, fileNm);
            customFileUtils.transferTo(p.getPic(), filePath);
        } catch (Exception e) {
            throw new RuntimeException("프로필 사진 수정 실패");
        }
        return fileNm;
    }
}
