package com.green.greengram.user;

import com.green.greengram.user.model.User;
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

    }

    @Test
    void updProfilePic() {

    }
}