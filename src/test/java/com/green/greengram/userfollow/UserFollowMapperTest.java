package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowEntity;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
class UserFollowMapperTest {

    @Autowired
    private UserFollowMapper mapper;

    final int RECORD_COUNT = 12;
    @Test
    @DisplayName("유저 팔로우 insert 테스트")
    void insUserFollow() {
        UserFollowReq p1 = new UserFollowReq(0, 0);
        List<UserFollowEntity> list1 = mapper.selUserFollowListForTest(p1);

        UserFollowReq p2 = new UserFollowReq(4, 5);
        int affrectedRows = mapper.insUserFollow(p2);
        assertEquals(1, affrectedRows);

        List<UserFollowEntity> list2 = mapper.selUserFollowListForTest(p1);
        int recordCountAfterFirstInsert = list2.size();
        assertEquals(1, recordCountAfterFirstInsert - list1.size(), "1. 실제 INSERT되지 않음!");

        List<UserFollowEntity> list3 = mapper.selUserFollowListForTest(p2);
        assertEquals(1, list3.size(), "p2값이 제대로 insert되지 않음");

        assertEquals(p2.getFromUserId(), list3.get(0).getFromUserId());
        assertEquals(p2.getToUserId(), list3.get(0).getToUserId());


        UserFollowReq p3 = new UserFollowReq(5, 1);
        int affrectedRows2 = mapper.insUserFollow(p3);
        assertEquals(1, affrectedRows2);
        List<UserFollowEntity> list4 = mapper.selUserFollowListForTest(p1);
        assertEquals(1, list4.size() - recordCountAfterFirstInsert, "2. 실제 INSERT되지 않음!");

        List<UserFollowEntity> list5 = mapper.selUserFollowListForTest(p3);
        assertEquals(1, list5.size(), "p3값이 제대로 insert되지 않음");
    }

    @Test
    @DisplayName("유저 팔로우 selelct 테스트")
    void selUserFollowListForTest() {

        //1. 전체 레코드 테스트
        UserFollowReq p1 = new UserFollowReq(0, 0);
        List<UserFollowEntity> list1 = mapper.selUserFollowListForTest(p1);
        assertEquals(RECORD_COUNT, list1.size(), "1. 레코드 수가 다르다.");

        UserFollowEntity record0 = list1.get(0);
        assertEquals(1, record0.getFromUserId(), "1. 0번 레코드 from_user_id 다름");
        assertEquals(2, record0.getToUserId(), "1. 0번 레코드 to_user_id 다름");

        assertEquals(new UserFollowEntity(1, 3, "2024-05-21 09:57:02")
                , list1.get(1), "1. 1번 레코드 값이 다름");


        //2. fromUserId = 1
        UserFollowReq p2 = new UserFollowReq(1, 0);
        List<UserFollowEntity> list2 = mapper.selUserFollowListForTest(p2);
        assertEquals(4, list2.size(), "2. 레코드 수가 다르다");
        assertEquals(new UserFollowEntity(1, 2, "2024-05-21 09:57:01")
                , list1.get(0), "2. 0번 레코드 값이 다름");
        assertEquals(new UserFollowEntity(1, 3, "2024-05-21 09:57:02")
                , list1.get(1), "2. 1번 레코드 값이 다름");

        //3. fromUserId = 300
        UserFollowReq p3 = new UserFollowReq(300, 0);
        List<UserFollowEntity> list3 = mapper.selUserFollowListForTest(p3);
        assertEquals(0, list3.size(), "3. 레코드가 넘어오면 안 됨");


        //4. toUserId = 1
        UserFollowReq p4 = new UserFollowReq(0, 1);
        List<UserFollowEntity> list4 = mapper.selUserFollowListForTest(p4);
        assertEquals(2, list4.size(), "4. 레코드 수가 다르다");
        assertEquals(new UserFollowEntity(2, 1, "2024-05-21 09:57:05")
                , list4.get(0), "4. 0번 레코드 값이 다름");
        assertEquals(new UserFollowEntity(3, 1, "2024-05-21 09:57:09")
                , list4.get(1), "4. 1번 레코드 값이 다름");
    }

    @Test
    void delUserFollowMe() {

    }

    @Test
    void delUserFollowYou() {
        UserFollowReq countParam = new UserFollowReq(0, 0);
        List<UserFollowEntity> totalList1 = mapper.selUserFollowListForTest(countParam);
        int recordCountOrigin = totalList1.size();

        //없는 pk 삭제
        UserFollowReq p2 = new UserFollowReq(10, 20);
        int affectedRows2 = mapper.delUserFollow(p2);
        assertEquals(0, affectedRows2, "삭제가 되면 안되나 삭제가 되었음");

        //1, 2 삭제
        UserFollowReq p3 = new UserFollowReq(1, 2);
        List<UserFollowEntity> p3List = mapper.selUserFollowListForTest(p3);
        assertEquals(1, p3List.size());

        int affectedRows3 = mapper.delUserFollow(p3);
        assertEquals(1, affectedRows3, "삭제 이상");

        List<UserFollowEntity> totalList2 = mapper.selUserFollowListForTest(countParam);
        int recordCountFirst = totalList2.size();
        assertEquals(1, recordCountOrigin - recordCountFirst);

        List<UserFollowEntity> p3List2 = mapper.selUserFollowListForTest(p3);
        assertEquals(0, p3List2.size());
    }

    @Test
    void delUserFollowYou2() {
        UserFollowReq countParam = new UserFollowReq(0, 0);
        List<UserFollowEntity> totalList1 = mapper.selUserFollowListForTest(countParam);
        int recordCountOrigin = totalList1.size();

        //없는 pk 삭제
        UserFollowReq p2 = new UserFollowReq(12, 22);
        int affectedRows2 = mapper.delUserFollow(p2);
        assertEquals(0, affectedRows2, "삭제가 되면 안되나 삭제가 되었음");

        //1, 2 삭제
        UserFollowReq p3 = new UserFollowReq(2, 3);
        List<UserFollowEntity> p3List = mapper.selUserFollowListForTest(p3);
        assertEquals(1, p3List.size());

        int affectedRows3 = mapper.delUserFollow(p3);
        assertEquals(1, affectedRows3, "삭제 이상");

        List<UserFollowEntity> totalList2 = mapper.selUserFollowListForTest(countParam);
        int recordCountFirst = totalList2.size();
        assertEquals(1, recordCountOrigin - recordCountFirst);

        List<UserFollowEntity> p3List2 = mapper.selUserFollowListForTest(p3);
        assertEquals(0, p3List2.size());
    }
}