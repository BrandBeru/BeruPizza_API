package org.beru.pizzeria.service;

import org.beru.pizzeria.persistence.entity.CustomerEntity;
import org.beru.pizzeria.persistence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public CustomerEntity findByEmail(String email){
        return this.customerRepository.findByEmail(email);
    }
}
