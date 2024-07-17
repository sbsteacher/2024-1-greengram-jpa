package com.green.greengram.entity;

import com.green.greengram.security.SignInProviderType;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity //이 클래스는 entity(테이블)이다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ColumnDefault("4")
    @Column(nullable = false) //not null
    private SignInProviderType providerType;

    @Column(length = 50, nullable = false) //문자열 길이, not null
    private String uid;
    @Column(length = 100, nullable = false)
    private String upw;
    @Column(length = 50, nullable = false)
    private String nm;
    @Column(length = 200, nullable = false)
    private String pic;

    @Column(nullable = false)
    @CreatedDate //JPA가 insert때 현재일시 값을 주입 (default current_timestamp() 속성을 추가하는 것이 아님)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
