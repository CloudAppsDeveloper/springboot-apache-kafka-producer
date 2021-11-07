package com.cinch.kafka.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinch.kafka.orders.producer.CustomerRequestService;


@RestController
@RequestMapping(value = "/customers")
public class CustomerRequestController {

    @Autowired
    CustomerRequestService customerRequestService;

    @GetMapping(value = "/customer/{id}")
    public ResponseEntity <String> getCustomers(@PathVariable int id) {
        String acknowledge = customerRequestService.getCustomers(id);
        return new ResponseEntity< >(acknowledge, HttpStatus.OK);
    }

}
