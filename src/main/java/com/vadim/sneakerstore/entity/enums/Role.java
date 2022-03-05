package com.vadim.sneakerstore.entity.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role {
    ADMIN(Stream.of(Permission.READ, Permission.WRITE, Permission.UPDATE, Permission.DELETE)
            .collect(Collectors.toSet())),
    USER(Stream.of(Permission.READ)
            .collect(Collectors.toSet()));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
