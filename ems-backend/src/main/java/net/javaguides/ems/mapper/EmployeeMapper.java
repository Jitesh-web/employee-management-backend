package net.javaguides.ems.mapper;

import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        EmployeeDto empDto = new EmployeeDto();
        empDto.setId(employee.getId());
        empDto.setFirstName(employee.getFirstName());
        empDto.setLastName(employee.getLastName());
        empDto.setEmail(employee.getEmail());
        empDto.setDepartment(employee.getDepartment());
        empDto.setAddress(AddressMapper.mapToAddressDto(employee.getAddress()));
        if (employee.getProjects() != null) {
            empDto.setProjects(
                    employee.getProjects()
                            .stream()
                            .map(ProjectMapper::mapToProjectDto)
                            .collect(Collectors.toList())
            );
        }
        if (employee.getSkills() != null) {
            empDto.setSkills(
                    employee.getSkills()
                            .stream()
                            .map(SkillMapper::mapToSkillDto)
                            .collect(Collectors.toList())
            );
        }

        return empDto;
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setAddress(AddressMapper.mapToAddress(employeeDto.getAddress()));
        if(employeeDto.getProjects()!=null){
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

        return employee;
    }
}
