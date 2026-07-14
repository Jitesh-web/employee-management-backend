package net.javaguides.ems.service;

import net.javaguides.ems.dto.EmployeeDto;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long employeeId);
    Page<EmployeeDto> getAllEmployees(String keyword, String department, Pageable pageable);
    EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto);
    void deleteEmployeeById(Long employeeId);

//    List<EmployeeDto> searchEmployees(String firstName);
//    List<EmployeeDto> filterByDepartment(String department);
}
