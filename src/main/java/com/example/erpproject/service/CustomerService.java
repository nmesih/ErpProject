package com.example.erpproject.service;

import com.example.erpproject.databases.entity.Customer;
import com.example.erpproject.databases.entity.Order;
import com.example.erpproject.databases.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer createPerson(String name, String surname, String address,
                                 String city, String country, int postalCode, List<Order> orderList) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setCountry(country);
        customer.setPostalCode(postalCode);
        customer.setOrders(orderList);

        customerRepository.save(customer);
        return customer;
    }

    public Customer getCustomerByCustomerId(long customerId) {

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        return customerOptional.orElse(null);
    }

    public Customer getCustomerByUUID(UUID uuid) {

        Optional<Customer> customerOptional = customerRepository.findByUuid(uuid);

        return customerOptional.orElse(null);
    }

    @Transactional
    public Customer updateCustomerByUUID(UUID uuid, Customer newCustomer) {

        Customer customer = getCustomerByUUID(uuid);

        if (customer != null) {

            customer.setName(newCustomer.getName());
            customer.setSurname(newCustomer.getSurname());
            customer.setAddress(newCustomer.getAddress());
            customer.setCity(newCustomer.getCity());
            customer.setCountry(newCustomer.getCountry());
            customer.setPostalCode(newCustomer.getPostalCode());
            customer.setOrders(newCustomer.getOrders());


            customerRepository.save(customer);

            return customer;

        } else
            return null;
    }

    @Transactional
    public Boolean deleteCustomerByUUID(UUID uuid) {
        Customer customer = getCustomerByUUID(uuid);

        if (customer != null) {

            customerRepository.deleteByUuid(uuid);

            return true;
        } else {
            return false;
        }
    }


}
