package com.example.erpproject.controller;

import com.example.erpproject.databases.entity.Customer;
import com.example.erpproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("customer-list")
    public ResponseEntity<List<Customer>> getAllCustomerList() {
        return new ResponseEntity<>(customerService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<Customer> customerGetByCustomerId(@PathVariable long customerId) {
        return new ResponseEntity<>(customerService.getCustomerByCustomerId(customerId), HttpStatus.OK);
    }

    @PostMapping("customer")
    public ResponseEntity<Customer> createPerson(@RequestBody Customer customer) {
        Customer customer1 = customerService.createPerson(customer.getName(),
                customer.getSurname(),customer.getAddress(), customer.getCity(),
                customer.getCountry(), customer.getPostalCode(), customer.getOrders());
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }

    @PutMapping("update/{uuid}")
    public ResponseEntity<Customer> updatePersonByUUID(@PathVariable UUID uuid,
                                                       @RequestBody Customer newCustomer) {

        Customer customer = customerService.updateCustomerByUUID(uuid, newCustomer);

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<String> deleteCustomerByUuid(@PathVariable UUID uuid) {
        Boolean isDeleted = customerService.deleteCustomerByUUID(uuid);
        if (isDeleted) {
            return new ResponseEntity<>("Customer başarıyla silindi", HttpStatus.OK);
        }
        return new ResponseEntity<>("Customer bulunamadı", HttpStatus.NOT_FOUND);
    }

}
