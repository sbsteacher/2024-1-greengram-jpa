package com.green.greengram.user;

import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//                                                  Entity, PK타입
public interface UserRepository extends JpaRepository<User, Long> {
}
