package com.sujeet.service;

import com.sujeet.dto.CustomerDTO;
import com.sujeet.entity.Customer;
import com.sujeet.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

     @Autowired
     private PasswordEncoder passwordEncoder;
    @Override
    public Customer saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirst_name(customerDTO.getFirst_name());
        customer.setLast_name(customerDTO.getLast_name());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setStreet(customerDTO.getStreet());
        customer.setCity(customerDTO.getCity());
        customer.setState(customerDTO.getState());
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public List<Customer> fetchCustomer() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()){
            throw new RuntimeException("Customers are not found");
        }
        return customers;
    }

    @Override
    public Customer modifyCustomer(CustomerDTO customerDTO, int customerId) {
//   Optional<Customer> customer = Optional.ofNullable(customerRepository.findByCustomerId(customerId));
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()){
            throw  new RuntimeException("customer is not found");
        }
        Customer customer1 = customer.get();
        customer1.setFirst_name(customerDTO.getFirst_name());
        customer1.setLast_name(customerDTO.getLast_name());
        customer1.setEmail(customerDTO.getEmail());
        customer1.setPhone(customerDTO.getPhone());
        customer1.setAddress(customerDTO.getAddress());
        customer1.setStreet(customerDTO.getStreet());
        customer1.setCity(customerDTO.getCity());
        customer1.setState(customerDTO.getState());
        customerRepository.save(customer1);
        return customer1;
    }

    @Override
    public void removeCustomer(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(!customer.isPresent()){
            throw new RuntimeException("customer is not found");
        }
        customerRepository.deleteById(customerId);
    }

    @Override
    public Customer fetchACustomer(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()){
            throw new RuntimeException("customer is not found");
        }
        return customer.get();
    }


}
