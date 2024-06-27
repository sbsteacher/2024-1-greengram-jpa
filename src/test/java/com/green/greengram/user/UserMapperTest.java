package com.green.greengram.user;

import com.green.greengram.user.model.User;
import com.green.greengram.user.model.UserInfoGetReq;
import com.green.greengram.user.model.UserInfoGetRes;
import com.green.greengram.user.model.UserProfilePatchReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void signInPost() {
        User user1 = mapper.signInPost("사용자1");
        List<User> userList1 = mapper.selTest(user1.getUserId());
        User user1Comp = userList1.get(0);
        assertEquals(user1Comp, user1);

        User user3 = mapper.signInPost("사용자3");
        List<User> userList3 = mapper.selTest(user3.getUserId());
        User user3Comp = userList3.get(0);
        assertEquals(user3Comp, user3);

        User userNo = mapper.signInPost("sdklsdfnkl");
        assertNull(userNo, "없는 사용자 레코드 넘어옴");
    }

    @Test
    void selProfileUserInfo() {
        UserInfoGetReq req1 = new UserInfoGetReq(2, 1);
        UserInfoGetRes res1 = mapper.selProfileUserInfo(req1);

        UserInfoGetRes expectedRes1 = new UserInfoGetRes();
        expectedRes1.setNm("홍길동");
        expectedRes1.setPic("2bb291c1-e57e-4455-98ec-fcc4edb7ae41.jfif");
        expectedRes1.setCreatedAt("2024-05-03 14:35:03");
        expectedRes1.setFeedCnt(4);
        expectedRes1.setFavCnt(2);
        expectedRes1.setFollowing(4);
        expectedRes1.setFollower(2);
        expectedRes1.setFollowState(3);

        assertEquals(expectedRes1, res1, "signedUserId:2, profileUserId:1 내용 상이");

        // 다른 UserInfoGetReq 값으로 체크 2~3 더 추가

        // 없는 UserInfoGetReq 값으로 null이 넘어오는지 체크
    }

    @Test
    void updProfilePicMe() {

    }

    @Test
    void updProfilePicYou() {
        //수정 되기 전 전체 리스트 가져옴
        List<User> beforeUserList = mapper.selTest(0);

        long modUserId = 1;
        String picName1 = "test.jpg";
        UserProfilePatchReq req1 = new UserProfilePatchReq();
        req1.setSignedUserId(modUserId);
        req1.setPicName(picName1);
        int affectedRows = mapper.updProfilePic(req1);

        assertEquals(1, affectedRows);

        List<User> userList1 = mapper.selTest(modUserId);
        User user1 = userList1.get(0);

        assertEquals(picName1, user1.getPic());

        //수정 된 후 전체 리스트 가져옴
        List<User> afterUserList = mapper.selTest(0);

        for(int i=0; i< beforeUserList.size(); i++) {
            User beforeUser = beforeUserList.get(i);
            if(beforeUser.getUserId() == modUserId) {
                assertNotEquals(beforeUser, afterUserList.get(i));
                continue;
            }
            assertEquals(beforeUser, afterUserList.get(i));
        }


    }

    @Test
    void updProfilePicNoUser() {
        List<User> beforeUserList = mapper.selTest(0);

        //없는 userId로 update 시도시 affectedRows 0이 넘어오는지 체크
        String picName1 = "test.jpg";
        UserProfilePatchReq req1 = new UserProfilePatchReq();
        req1.setSignedUserId(100);
        req1.setPicName(picName1);
        int affectedRows = mapper.updProfilePic(req1);

        assertEquals(0, affectedRows);

        List<User> afterUserList = mapper.selTest(0);

        for(int i=0; i< beforeUserList.size(); i++) {
            assertEquals(beforeUserList.get(i), afterUserList.get(i));
        }
    }
}