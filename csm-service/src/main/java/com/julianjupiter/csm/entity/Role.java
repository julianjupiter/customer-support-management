package com.julianjupiter.csm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.Set;

/**
 * @author Julian Jupiter
 */
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleType name;
    private String description;
    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles;
    private Instant createdAt;
    private Instant updatedAt;

    public Integer getId() {
        return id;
    }

    public Role setId(Integer id) {
        this.id = id;
        return this;
    }

    public RoleType getName() {
        return name;
    }

    public Role setName(RoleType name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public Role setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Role setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Role setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
