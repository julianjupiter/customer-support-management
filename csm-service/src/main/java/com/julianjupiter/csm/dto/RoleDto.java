package com.julianjupiter.csm.dto;

import com.julianjupiter.csm.entity.RoleType;

/**
 * @author Julian Jupiter
 */
public record RoleDto(Integer id, RoleType name, String description) {
}
