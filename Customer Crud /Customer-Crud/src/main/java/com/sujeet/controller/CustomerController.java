package com.sujeet.controller;

import com.sujeet.dto.CustomerDTO;
import com.sujeet.entity.Customer;
import com.sujeet.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/customer")
//@RequestMapping("/auth")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

   // @Autowired
    //    private UserInfoService service;
    //
    //    @Autowired
    //    private JwtService jwtService;
    //
    //    @Autowired
    //    private AuthenticationManager authenticationManager;

    //@PostMapping("/generateToken")
    //    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    //        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    //        if (authentication.isAuthenticated()) {
    //            return jwtService.generateToken(authRequest.getUsername());
    //        } else {
    //            throw new UsernameNotFoundException("invalid user request !");
    //        }
    //    }

    @PostMapping("/create")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.saveCustomer(customerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get_customer_list")
    public ResponseEntity<List<Customer>> getCustomer(){
        return new ResponseEntity<>(customerService.fetchCustomer(), HttpStatus.OK);
    }

    @GetMapping("/get/{customerId}")
    public ResponseEntity<Customer> getACustomer(@PathVariable int customerId){
        return new ResponseEntity<>(customerService.fetchACustomer(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public void deleteCustomer(@PathVariable int customerId){
        customerService.removeCustomer(customerId);

    }

    @PutMapping("/update/{customerId}")
    public Customer updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable int customerId){
        return customerService.modifyCustomer(customerDTO, customerId);
    }
}
