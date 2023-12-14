package com.sujeet.service;

import com.sujeet.dto.CustomerDTO;
import com.sujeet.entity.Customer;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer saveCustomer(CustomerDTO customerDTO);

    List<Customer> fetchCustomer();

    Customer modifyCustomer(CustomerDTO customerDTO, int customerId);

    void removeCustomer(int customerId);

    Customer fetchACustomer(int customerId);
}