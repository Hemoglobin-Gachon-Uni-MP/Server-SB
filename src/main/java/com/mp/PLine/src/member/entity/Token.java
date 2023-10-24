package com.mp.PLine.src.member.entity;

import com.mp.PLine.utils.entity.BaseEntity;
import com.mp.PLine.utils.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Token extends BaseEntity {
    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;

    private String key;
    private String role;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Token(Member member, String key, String role, Status status) {
        this.member = member;
        this.key = key;
        this.role = role;
        this.status = status;
    }

    public static Token of(Member member, String key, String role, Status status) {
        return Token.builder()
                .member(member)
                .key(key)
                .role(role)
                .status(status)
                .build();
    }
}