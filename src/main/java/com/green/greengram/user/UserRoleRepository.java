package com.green.greengram.user;

import com.green.greengram.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    //쿼리 메소드 select * from user_role where user_id = #{user_id}
    //필드명이 아닌 컬럼명 작성해야 함
    List<UserRole> findAllByUserId(Long userId);
}
