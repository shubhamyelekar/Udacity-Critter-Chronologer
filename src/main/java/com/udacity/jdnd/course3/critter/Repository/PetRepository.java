package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("Select p from Pet p where p.customer = :customer")
    List<Pet> findPetsIdByCustomer(@Param("customer") Customer c);

}
