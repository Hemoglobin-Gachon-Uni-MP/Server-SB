package com.mp.PLine.source.member.entity;

public enum Role {
    GUEST("ROLE_GUEST"),
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
