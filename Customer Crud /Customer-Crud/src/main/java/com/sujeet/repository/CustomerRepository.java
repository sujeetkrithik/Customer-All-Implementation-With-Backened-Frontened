package com.sujeet.repository;

import com.sujeet.auth.AuthRequest;
import com.sujeet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByCustomerId(int customerId);

    void deleteByCustomerId(int customerId);

    Optional<AuthRequest> findByName(String username);
}
