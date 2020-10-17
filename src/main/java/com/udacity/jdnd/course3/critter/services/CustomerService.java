package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;


    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            for (Long id : petIds) {
                pets.add(petService.getPetById(id));
            }
        }
        customer.setPets(pets);
        BeanUtils.copyProperties(customerDTO, customer);
        CustomerDTO customerDTO1 = new CustomerDTO();
        BeanUtils.copyProperties(customerRepository.save(customer), customerDTO1);
        return customerDTO1;

    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }


    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);
            List<Long> petIds = new ArrayList<>();
            for (Pet pet : customer.getPets()) {
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;

    }

    public CustomerDTO getCustomerByPetid(Long id) {
        Pet p = petService.getPetById(id);
        Customer customer = customerRepository.findCustomerByPetDTO(p);
//        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = new ArrayList<>();
        for (Pet pet : customer.getPets()) {
            petIds.add(pet.getId());
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

}
