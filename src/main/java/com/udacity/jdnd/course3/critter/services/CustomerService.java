package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;


    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);

    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();

    }

    public Customer getCustomerByPetid(Long id) {
        Pet p = petService.getPetById(id);
        return customerRepository.findCustomerByPetDTO(p);
    }

}
