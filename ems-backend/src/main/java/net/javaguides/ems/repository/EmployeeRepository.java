package net.javaguides.ems.repository;

import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //Derived Query
    Page<Employee> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
    Page<Employee> findByDepartmentIgnoreCase(String department, Pageable pageable);
    Page<Employee> findByFirstNameContainingIgnoreCaseAndDepartmentIgnoreCase(
            String keyword,
            String department,
            Pageable pageable);
}
