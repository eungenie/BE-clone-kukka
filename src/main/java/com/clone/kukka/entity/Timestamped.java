package com.clone.kukka.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // get 함수를 자동 생성합니다.
@MappedSuperclass // CreatedAt과 ModifiedAt이 상속한 클래스에 멤버 변수가 되도록 만들어주는 기능. 멤버 변수가 컬럼이 되도록 함
@EntityListeners(AuditingEntityListener.class) // 변경되었을 때 자동으로 기록
public abstract class Timestamped {
    //별도로 생성하는 것은 없으므로 이것이 상속이 되어 다른곳에서 사용된다는 개념을 좀 더 명시적으로 표현하기 위해 abstract
    @CreatedDate // 최초 생성 시점
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 변경 시점
    private LocalDateTime modifiedAt;
}
