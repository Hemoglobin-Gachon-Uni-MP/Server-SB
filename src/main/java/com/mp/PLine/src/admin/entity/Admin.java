package com.mp.PLine.src.admin.entity;

import com.mp.PLine.src.member.entity.Role;
import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String adminKey;
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Admin from(String adminKey) {
        return Admin.builder()
                .adminKey(adminKey)
                .role(Role.ADMIN)
                .status(Status.A)
                .build();
    }
}