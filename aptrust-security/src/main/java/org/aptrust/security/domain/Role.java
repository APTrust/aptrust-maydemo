/*
 * Copyright (c) 2009-2010 DuraSpace. All rights reserved.
 */
package org.aptrust.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import java.util.HashSet;
import java.util.Set;


public enum Role {
    ROLE_ROOT("Root"),
    ROLE_USER("User"),
    ROLE_ANONYMOUS("Anonymous");

    private GrantedAuthority authority;
    private String displayName;

    Role(String displayName) {
        this.authority = new GrantedAuthorityImpl(name());
        this.displayName = displayName;
    }

    public GrantedAuthority authority() {
        return this.authority;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<Role> getRoleHierarchy() {
        Set<Role> hierarchy = new HashSet<Role>();
        switch (this) {
            case ROLE_ROOT:
                hierarchy.add(ROLE_ROOT);
            case ROLE_USER:
                hierarchy.add(ROLE_USER);
            case ROLE_ANONYMOUS:
                hierarchy.add(ROLE_ANONYMOUS);
                break;
        }

        return hierarchy;
    }

    public static Role highestRole(Set<Role> roles) {
        Role highest = null;
        if (roles.contains(Role.ROLE_ROOT)) {
            return Role.ROLE_ROOT;
        } else if (roles.contains(Role.ROLE_USER)) {
            return Role.ROLE_USER;
        } else if (roles.contains(Role.ROLE_ANONYMOUS)) {
            return Role.ROLE_ANONYMOUS;
        }
        return highest;
    }
}
