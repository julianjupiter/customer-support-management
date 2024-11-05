package com.julianjupiter.csm.repository;

import com.julianjupiter.csm.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Julian Jupiter
 */
public interface PriorityRepository extends JpaRepository<Priority, String> {
}
