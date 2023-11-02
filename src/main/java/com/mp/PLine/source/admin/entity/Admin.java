package com.mp.PLine.source.admin.entity;

import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Admin extends BaseEntity {
    private String adminKey;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Admin(String adminKey, Status status) {
        this.adminKey = adminKey;
        this.status = status;
    }

    public static Admin of(String adminKey, Status status) {
        return Admin.builder()
                .adminKey(adminKey)
                .status(status)
                .build();
    }
}