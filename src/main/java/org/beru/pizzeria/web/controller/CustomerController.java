package org.beru.pizzeria.web.controller;

import java.util.List;

import org.beru.pizzeria.persistence.entity.CustomerEntity;
import org.beru.pizzeria.persistence.entity.OrderEntity;
import org.beru.pizzeria.service.CustomerService;
import org.beru.pizzeria.service.OrderService;
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
    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerEntity> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(this.customerService.findByEmail(email));
    }
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderEntity>> getCostumerOrders(@PathVariable String id){
        return ResponseEntity.ok(this.orderService.getCustomerOrders(id));
    }
}
