package com.io.rol.member.domain.entity;

// 권한
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
