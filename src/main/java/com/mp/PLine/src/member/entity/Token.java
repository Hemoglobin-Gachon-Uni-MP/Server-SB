package com.mp.PLine.src.member.entity;

import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.management.relation.Role;
import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Token extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String tokenKey;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Token(Member member, String tokenKey, Roles roles, Status status) {
        this.member = member;
        this.tokenKey = tokenKey;
        this.roles = roles;
        this.status = status;
    }

    public static Token of(Member member, String tokenKey, Roles roles, Status status) {
        return Token.builder()
                .member(member)
                .tokenKey(tokenKey)
                .roles(roles)
                .status(status)
                .build();
    }
}