package study.datajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass //사용하지 않으면 테이블에는 생성이 되지 않음
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
