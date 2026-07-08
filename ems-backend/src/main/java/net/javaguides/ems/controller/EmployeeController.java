package net.javaguides.ems.controller;

import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Adding cross origin to all the frontend to make api call to avoid CROS erro
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    //Create a new employee
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    //Get a employee by Id
    @GetMapping("/{Id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("Id") Long employeeId) {
        EmployeeDto employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("Id") Long employeeId, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto employee = employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("Id") Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
        return ResponseEntity.ok("Employee deleted successfully with employee id: " + employeeId);
    }
}
