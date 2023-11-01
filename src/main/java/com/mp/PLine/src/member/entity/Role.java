package com.mp.PLine.src.member.entity;

public enum Role {
    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private String label;

    private Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
