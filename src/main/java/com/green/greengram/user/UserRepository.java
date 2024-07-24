package com.green.greengram.user;

import com.green.greengram.entity.User;
import com.green.greengram.security.SignInProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
//                                                  Entity, PK타입
public interface UserRepository extends JpaRepository<User, Long> {
    //쿼리 메소드 select * from user where provider_type = #{providerType} and uid = #{uid}
    User findUserByProviderTypeAndUid(SignInProviderType providerType, String uid);
}
