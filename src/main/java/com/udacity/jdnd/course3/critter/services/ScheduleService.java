package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.models.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        for (Long id : scheduleDTO.getEmployeeIds()) {
            employees.add(employeeService.getEmployee(id));
        }

        for (Long id : scheduleDTO.getPetIds()) {
            pets.add(petService.getPetById(id));
        }

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        scheduleRepository.save(schedule);
        return scheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        return getDTOSFromSchedule(schedules);
    }

    public List<ScheduleDTO> getScheduleForPet(Long id) {
        Pet pet = petService.getPetById(id);
        List<Schedule> schedules = scheduleRepository.findSchedulesByPetsIsContaining(pet);
        return getDTOSFromSchedule(schedules);
    }

    public List<ScheduleDTO> getScheduleForEmployee(Long id) {
        Employee employee = employeeService.getEmployee(id);
        List<Schedule> schedules = scheduleRepository.findSchedulesByEmployeesIsContaining(employee);
        return getDTOSFromSchedule(schedules);
    }

    public List<ScheduleDTO> getScheduleForCustomer(Long id) {
        List<Pet> pets = petService.listPetsByOwnerid(id);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Pet pet : pets) {
            scheduleDTOS.addAll(getScheduleForPet(pet.getId()));
        }
        return scheduleDTOS;
    }


    private List<ScheduleDTO> getDTOSFromSchedule(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setActivities(schedule.getActivities());
            scheduleDTO.setDate(schedule.getDate());
            List<Long> employeeIds = new ArrayList<>();
            List<Long> petIds = new ArrayList<>();
            for (Employee employee : schedule.getEmployees()) {
                employeeIds.add(employee.getId());
            }

            for (Pet pet : schedule.getPets()) {
                petIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setEmployeeIds(employeeIds);
            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;

    }

    public boolean isEmployeeAvailable(Employee employee, LocalDate localDate) {
        return scheduleRepository.findSchedulesByEmployeesIsContainingAndDateIs(employee, localDate).size() <= 0;
    }
}
