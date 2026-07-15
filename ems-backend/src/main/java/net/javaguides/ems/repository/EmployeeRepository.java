package net.javaguides.ems.repository;

import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //Derived Query
    //Page<Employee> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
    //Page<Employee> findByDepartmentIgnoreCase(String department, Pageable pageable);
//    Page<Employee> findByFirstNameContainingIgnoreCaseAndDepartmentIgnoreCase(
//            String keyword,
//            String department,
//            Pageable pageable);

    //JPQL Query
//    @Query("""
//        SELECT e
//        FROM Employee e
//        WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
//        """)
//    Page<Employee> searchEmployeesJPQL(
//            @Param("keyword") String keyword,
//            Pageable pageable);

//    @Query("""
//        SELECT e
//        FROM Employee e
//        WHERE LOWER(e.department) = LOWER(:department)
//        """)
//    Page<Employee> filterEmployeesJPQL(
//            @Param("department") String department,
//            Pageable pageable);

//    @Query("""
//        SELECT e
//        FROM Employee e
//        WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
//                AND LOWER(e.department) = LOWER(:department)
//        """)
//    Page<Employee> searchAndFilterEmployeesJPQL(
//            @Param("keyword") String keyword,
//            @Param("department") String department,
//            Pageable pageable);

    //Native Query
//    @Query(value = """
//        SELECT *
//        FROM employees
//        WHERE LOWER(first_name)
//              LIKE LOWER(CONCAT('%', :keyword, '%'))
//        """,
//            nativeQuery = true)
//    Page<Employee> searchEmployeesNative(
//            @Param("keyword") String keyword,
//            Pageable pageable);
//
//    @Query(value = """
//        SELECT *
//        FROM employees
//        WHERE LOWER(department)
//              = LOWER(:department)
//        """,
//            nativeQuery = true)
//    Page<Employee> filterEmployeesNative(
//            @Param("department") String department,
//            Pageable pageable);
//
//    @Query(value = """
//        SELECT *
//        FROM employees
//        WHERE LOWER(first_name)
//              LIKE LOWER(CONCAT('%', :keyword, '%'))
//                AND LOWER(department)
//              = LOWER(:department)
//        """,
//            nativeQuery = true)
//    Page<Employee> searchAndFilterEmployeesNative(
//            @Param("keyword") String keyword,
//            @Param("department") String department,
//            Pageable pageable);

    //Named Query
    @Query(name = "Employee.searchEmployees")
    Page<Employee> searchEmployeesNamed(
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query(name = "Employee.filterEmployees")
    Page<Employee> filterEmployeesNamed(
            @Param("department") String department,
            Pageable pageable);

    @Query(name = "Employee.searchAndFilterEmployees")
    Page<Employee> searchAndFilterEmployeesNamed(
            @Param("keyword") String keyword,
            @Param("department") String department,
            Pageable pageable);
}
