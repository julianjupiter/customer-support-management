package com.julianjupiter.csm.repository;

import com.julianjupiter.csm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Julian Jupiter
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
