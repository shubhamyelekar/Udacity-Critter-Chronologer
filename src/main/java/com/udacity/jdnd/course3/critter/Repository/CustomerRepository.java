package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.models.Customer;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("Select c.pets from Customer c")
    List<Pet> getAllpets();

    @Query("Select c from Customer c where :pet member of c.pets")
    Customer findCustomerByPetDTO(@Param("pet") Pet p);

    Customer findCustomerByPetsContaining(Pet p);
}
