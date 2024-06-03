package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;

    public int signUpPostReq(MultipartFile pic, SignUpPostReq p){
        String saveFileName = customFileUtils.makeRandomFileName(pic);
        p.setPic(saveFileName);
        String password = BCrypt.hashpw(p.getUpw(),BCrypt.gensalt());
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

    public SignInPostRes signInPost (SignInPostReq p) {
        User user = mapper.signInPost(p.getUid());

        if (user == null) {
            throw new RuntimeException("아이디를 확인해주세요.");
        }
        if (!BCrypt.checkpw(p.getUpw(), user.getUpw())) {
            throw new RuntimeException("비밀번호를 확인해주세요.");
        }
        return SignInPostRes.builder()
                .userId(user.getUserId())
                .nm(user.getNm())
                .pic(user.getPic())
                .build();
    }

    public UserInfoGetRes getUserInfo(UserInfoGetReq p) {
        return mapper.selProfileUserInfo(p);
//        UserInfoGetRes res2 = new UserInfoGetRes();
//        res2.setNm("test2");
//        return res2;
    }


    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {
        String fileNm = customFileUtils.makeRandomFileName(p.getPic());
        p.setPicName(fileNm);
        mapper.updProfilePic(p);

        //기존 폴더 삭제
        try {
            String midPath = String.format("user/%d", p.getSignedUserId());
            String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, midPath);
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
