package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.models.Employee;
import com.udacity.jdnd.course3.critter.models.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ScheduleService scheduleService;


    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public Employee getEmployeeDto(Long id) {
        return employeeRepository.findById(id).get();
    }

    public void setAvailability(Set<DayOfWeek> days, Long id) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setDaysAvailable(days);
        employeeRepository.save(employee);

    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            if (employee.getSkills().containsAll(employeeRequestDTO.getSkills()) && scheduleService.isEmployeeAvailable(employee, employeeRequestDTO.getDate())) {
                employees.add(employee);
            }
        }

        return employees;
    }

}
