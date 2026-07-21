package net.javaguides.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;
import net.javaguides.ems.exception.ResourceNotFoundException;
import net.javaguides.ems.mapper.AddressMapper;
import net.javaguides.ems.mapper.EmployeeMapper;
import net.javaguides.ems.mapper.ProjectMapper;
import net.javaguides.ems.mapper.SkillMapper;
import net.javaguides.ems.repository.EmployeeRepository;
import net.javaguides.ems.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        LOGGER.debug("Mapping EmployeeDto to Employee entity");
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        LOGGER.debug("Saving employee to database");
        Employee savedEmployee = employeeRepository.save(employee);

        if (true) {
            throw new RuntimeException("Testing Transaction Rollback");
        }

        LOGGER.debug("Employee entity saved successfully: {}", savedEmployee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public Page<EmployeeDto> getAllEmployees(String keyword,
                                             String department,
                                             Pageable pageable) {

        LOGGER.info("Fetching employees with keyword: {}, department: {}",
                keyword, department);

        Page<Employee> employees;

        if (keyword != null && !keyword.isBlank()
                && department != null && !department.isBlank()) {

            employees = employeeRepository
                    .searchAndFilterEmployeesNamed(
                            keyword,
                            department,
                            pageable);

        } else if (keyword != null && !keyword.isBlank()) {

            employees = employeeRepository
                    .searchEmployeesNamed(keyword, pageable);

        } else if (department != null && !department.isBlank()) {

            employees = employeeRepository
                    .filterEmployeesNamed(department, pageable);

        } else {

            employees = employeeRepository.findAll(pageable);
        }

        if (employees.isEmpty()) {
            LOGGER.warn("No employees found");
        }

        return employees.map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found to update with id: " + employeeId));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        if (employeeDto.getAddress() != null) {
            employee.setAddress(AddressMapper.mapToAddress(employeeDto.getAddress()));
        }
        if (employeeDto.getProjects() != null) {
            employee.setProjects(
                    employeeDto.getProjects()
                            .stream()
                            .map(ProjectMapper::mapToProject)
                            .collect(Collectors.toList())
            );
            employee.getProjects().forEach(project ->
                    project.setEmployee(employee));
        }
        if (employeeDto.getSkills() != null) {

            employee.setSkills(
                    employeeDto.getSkills()
                            .stream()
                            .map(SkillMapper::mapToSkill)
                            .collect(Collectors.toList())
            );

            employee.getSkills().forEach(skill ->
                    skill.setEmployees(List.of(employee)));
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found to delete with id: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }

//    @Override
//    public List<EmployeeDto> searchEmployees(String firstName) {
//        List<Employee> employeeList = employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
//        return employeeList.stream()
//                .map(employee -> EmployeeMapper.mapToEmployeeDto(employee))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<EmployeeDto> filterByDepartment(String department) {
//        List<Employee> employeeList = employeeRepository.findByDepartmentIgnoreCase(department);
//        return employeeList.stream()
//                .map(employee -> EmployeeMapper.mapToEmployeeDto(employee))
//                .collect(Collectors.toList());
//    }
}
