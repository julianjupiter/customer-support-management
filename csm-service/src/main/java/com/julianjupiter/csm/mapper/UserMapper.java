package com.julianjupiter.csm.mapper;

import com.julianjupiter.csm.dto.CreateUserRequestDto;
import com.julianjupiter.csm.dto.UserDto;
import com.julianjupiter.csm.entity.Role;
import com.julianjupiter.csm.entity.User;
import com.julianjupiter.csm.entity.UserRole;
import com.julianjupiter.csm.util.Uuid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Julian Jupiter
 */
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    public UserMapper(PasswordEncoder passwordEncoder, RoleMapper roleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.roleMapper = roleMapper;
    }

    public User map(CreateUserRequestDto createUserRequestDto, Role role) {
        var user = new User()
                .setId(Uuid.create().toString())
                .setUsername(createUserRequestDto.username())
                .setPassword(this.passwordEncoder.encode(createUserRequestDto.password()))
                .setEmail(createUserRequestDto.email())
                .setFirstName(createUserRequestDto.firstName())
                .setLastName(createUserRequestDto.lastName())
                .setAccountNonExpired(TRUE)
                .setAccountNonLocked(TRUE)
                .setCredentialsNonExpired(TRUE)
                .setEnabled(TRUE)
                .setEmailVerified(FALSE)
                .setMobileNumberVerified(FALSE);
        var userRole = new UserRole()
                .setId(Uuid.create().toString())
                .setUser(user)
                .setRole(role);
        user.setUserRoles(Set.of(userRole));

        return user;
    }

    public UserDto map(User user) {
        return new UserDto(
                UUID.fromString(user.getId()),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getExtensionName(),
                user.getMobileNumber(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.isEmailVerified(),
                user.isMobileNumberVerified(),
                user.getUserRoles().stream()
                        .map(userRole -> this.roleMapper.map(userRole.getRole()))
                        .collect(Collectors.toSet()),
                user.getCreatedBy(),
                user.getCreatedAt(),
                user.getUpdatedBy(),
                user.getUpdatedAt()
        );
    }
}
