package com.udacity.jdnd.course3.critter.models;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pet_table")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    private PetType type;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    private LocalDate birthDate;
    private String notes;

}
