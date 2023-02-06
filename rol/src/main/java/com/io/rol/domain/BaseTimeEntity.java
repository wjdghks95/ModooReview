package com.io.rol.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @PrePersist // 영속(new/transient) 상태에서 영속(managed) 상태가 되는 시점 이전에 실행
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = this.createdDate;
    }

    @PreUpdate // 영속 상태의 엔티티를 이용하여 데이터 업데이트를 수행하기 이전에 실행
    public void onPreUpdate(){
        this.lastModifiedDate = LocalDateTime.now();
    }
}

