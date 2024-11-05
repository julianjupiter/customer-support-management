package com.julianjupiter.csm.repository;

import com.julianjupiter.csm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Julian Jupiter
 */
public interface UserRepository extends JpaRepository<User, String> {
}
