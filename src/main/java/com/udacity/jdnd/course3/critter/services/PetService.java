package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        Pet returnedPet = petRepository.save(pet);
        Customer customer = returnedPet.getCustomer();
        customer.addPet(returnedPet);
        customerRepository.save(customer);
        return returnedPet;

    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByOwnerId(Long id) {
        Customer c = customerService.getCustomerById(id);
        return petRepository.findPetsIdByCustomer(c);
    }

    public Pet getPetById(Long id) {
        return petRepository.getOne(id);
    }

    public List<Pet> listPetsByOwnerid(Long id) {
        return petRepository.findPetsIdByCustomer(customerService.getCustomerById(id));
    }

}
