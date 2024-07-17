package com.green.greengram.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@MappedSuperclass //부모클래스랑 맵핑 가능하게
@EntityListeners(AuditingEntityListener.class)

public class CreatedAt {
}
