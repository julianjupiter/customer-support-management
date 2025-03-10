package com.julianjupiter.csm.service;

import com.julianjupiter.csm.entity.User;
import com.julianjupiter.csm.repository.UserRepository;
import com.julianjupiter.csm.security.JwtUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * @author Julian Jupiter
 */
@Service
@Transactional
class DefaultUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(user -> new JwtUser(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        this.createName(user),
                        user.getFirstName(),
                        user.getLastName(),
                        user.isEnabled(),
                        user.isAccountNonExpired(),
                        user.isCredentialsNonExpired(),
                        user.isAccountNonLocked(),
                        user.getUserRoles().stream()
                                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName().name()))
                                .collect(Collectors.toSet())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }

    private String createName(User user) {
        var firstName = user.getFirstName();
        var lastName = user.getLastName();

        if (firstName != null && lastName != null) {
            if (!firstName.isBlank() && !lastName.isBlank()) {
                return firstName + " " + lastName;
            } else if (!firstName.isBlank()) {
                return firstName;
            } else if (!lastName.isBlank()) {
                return lastName;
            } else {
                return null;
            }
        } else if (firstName != null && !firstName.isBlank()) {
            return firstName;
        } else if (lastName != null && !lastName.isBlank()) {
            return lastName;
        } else {
            return null;
        }
    }
}
