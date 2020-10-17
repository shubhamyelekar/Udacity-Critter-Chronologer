package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findSchedulesByPetsIsContaining(Pet pet);

    List<Schedule> findSchedulesByEmployeesIsContaining(Employee employee);

    List<Schedule> findSchedulesByEmployeesIsContainingAndDateIs(Employee employee, LocalDate localDate);
}
