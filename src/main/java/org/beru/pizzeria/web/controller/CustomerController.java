package org.beru.pizzeria.web.controller;

import org.beru.pizzeria.persistence.entity.CustomerEntity;
import org.beru.pizzeria.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerEntity> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(this.customerService.findByEmail(email));
    }
}
