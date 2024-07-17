package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MainCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mainCodeId;

    @Column(length = 20, nullable = false)
    private String cdName;

    @Column(length = 30)
    private String description;
}
