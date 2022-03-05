package com.vadim.sneakerstore.entity.enums;

public enum Permission {
    DELETE("delete"),
    UPDATE("update"),
    READ("read"),
    WRITE("write");

    public String getPermission() {
        return permission;
    }

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
