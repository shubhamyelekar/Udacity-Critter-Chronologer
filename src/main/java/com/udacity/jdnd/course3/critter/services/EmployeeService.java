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

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ScheduleService scheduleService;


    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        BeanUtils.copyProperties(employeeRepository.save(employee), employeeDTO1);
        return employeeDTO1;
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public EmployeeDTO getEmployeeDto(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public void setAvailability(Set<DayOfWeek> days, Long id) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setDaysAvailable(days);
        employeeRepository.save(employee);

    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSkills().containsAll(employeeRequestDTO.getSkills()) && scheduleService.isEmployeeAvailable(employee, employeeRequestDTO.getDate())) {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employee, employeeDTO);
                employeeDTOS.add(employeeDTO);
            }
        }

        return employeeDTOS;
    }

}
