package com.julianjupiter.csm.mapper;

import com.julianjupiter.csm.dto.RoleDto;
import com.julianjupiter.csm.entity.Role;
import org.springframework.stereotype.Component;

/**
 * @author Julian Jupiter
 */
@Component
public class RoleMapper {
    public RoleDto map(Role role) {
        return new RoleDto(role.getId(), role.getName(), role.getDescription());
    }
}
