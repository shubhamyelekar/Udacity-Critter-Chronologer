package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    public PetDTO savePet(PetDTO petDTO)  {
        Pet pet = new Pet();
        Customer c = customerService.getCustomerById(petDTO.getOwnerId());
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(c);
        PetDTO petDTO1 = new PetDTO();
        petDTO1.setOwnerId(c.getId());
        BeanUtils.copyProperties(petRepository.save(pet), petDTO1);
        return petDTO1;
    }

    public List<PetDTO> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        List<PetDTO> petsDTO = new ArrayList<PetDTO>();
        for (Pet pet : pets) {
            PetDTO petDTO = new PetDTO();
            petDTO.setOwnerId(pet.getCustomer().getId());
            BeanUtils.copyProperties(pet, petDTO);
            petsDTO.add(petDTO);
        }

        return petsDTO;
    }

    public List<PetDTO> getAllPetsByOwnerId(Long id) {
        Customer c = customerService.getCustomerById(id);
        List<Pet> pets = petRepository.findPetsIdByCustomer(c);
        List<PetDTO> petsDTO = new ArrayList<PetDTO>();
        for (Pet pet : pets) {
            PetDTO petDTO = new PetDTO();
            petDTO.setOwnerId(id);
            BeanUtils.copyProperties(pet, petDTO);
            petsDTO.add(petDTO);
        }
        return petsDTO;

    }

    public Pet getPetById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            return pet.get();
        }
        return null;
    }

    public List<Pet> listPetsByOwnerid(Long id) {
        return petRepository.findPetsIdByCustomer(customerService.getCustomerById(id));
    }

    public PetDTO getPetDtoById(Long id) {
        Pet pet = petRepository.findById(id).get();
        PetDTO petDTO = new PetDTO();
        petDTO.setOwnerId(pet.getCustomer().getId());
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

}
