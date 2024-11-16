package com.julianjupiter.csm.entity;

/**
 * @author Julian Jupiter
 */
public enum RoleType {
    ROLE_ADMIN(1), ROLE_AGENT(2);

    private final int id;

    RoleType(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}
