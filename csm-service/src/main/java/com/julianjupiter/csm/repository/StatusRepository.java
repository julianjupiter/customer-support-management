package com.julianjupiter.csm.repository;

import com.julianjupiter.csm.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Julian Jupiter
 */
public interface StatusRepository extends JpaRepository<Status, String> {
}
