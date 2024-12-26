package com.table.hotpack3.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@Getter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plan_id", updatable=false)
    private Long planId;

    @Column(name="plan_title", nullable=false)
    private String planTitle;

    @Column(name="plan_content", nullable = false)
    private String planContent;

    @CreatedDate
    @Column(name="regdate")
    private LocalDateTime regdate;

    @LastModifiedDate
    @Column(name="updatedate")
    private LocalDateTime updateDate;

    @Builder
    public Plan(String planTitle, String planContent, Date regdate, Date updateDate) {
        this.planTitle= planTitle;
        this.planContent= planContent;
    }

    public void update(String planTitle, String planContent) {
        this.planTitle=planTitle;
        this.planContent=planContent;
    }
}
