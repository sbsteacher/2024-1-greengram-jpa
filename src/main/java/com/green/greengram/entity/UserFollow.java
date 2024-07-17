package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = { "from_user_id", "to_user_id" }
        )
    }
)
public class UserFollow extends CreatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFollowId;

    @ManyToOne //Many(UserFolow) To One(User)
    @JoinColumn(name = "from_user_id") //조인 컬럼명 (컬럼명이 됨)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

}
