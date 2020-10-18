package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.models.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long id) {
        Pet pet = petService.getPetById(id);
        return scheduleRepository.findSchedulesByPetsIsContaining(pet);
    }

    public List<Schedule> getScheduleForEmployee(Long id) {
        Employee employee = employeeService.getEmployee(id);
         return scheduleRepository.findSchedulesByEmployeesIsContaining(employee);
    }

    public List<Schedule> getScheduleForCustomer(Long id) {
        List<Pet> pets = petService.listPetsByOwnerid(id);
        List<Schedule> schedules= new ArrayList<>();
        for (Pet pet : pets) {
            schedules.addAll(getScheduleForPet(pet.getId()));
        }
        return schedules;
    }



    public boolean isEmployeeAvailable(Employee employee, LocalDate localDate) {
        return scheduleRepository.findSchedulesByEmployeesIsContainingAndDateIs(employee, localDate).size() <= 0;
    }
}
