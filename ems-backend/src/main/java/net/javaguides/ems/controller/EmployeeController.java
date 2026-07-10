package net.javaguides.ems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Adding cross origin to all the frontend to make api call to avoid CROS error
@Tag(
        name = "Employee Management",
        description = "REST APIs for Employee Management"
)
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    //Create a new employee
    @Operation(
            summary = "Create Employee",
            description = "Creates a new employee and stores it in the database."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee created successfully"
            )
    })
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        LOGGER.info("Received request to create employee");
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        LOGGER.info("Employee created successfully with id: {}", savedEmployee.getId());
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Get a employee by Id
    @Operation(
            summary = "Get Employee by ID",
            description = "Retrieves an employee using the employee ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee found successfully"
            )
    })
    @GetMapping("/{Id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("Id") Long employeeId) {
        EmployeeDto employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all Employees",
            description = "Retrieves all employees."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee retrieved successfully"
            )
    })
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Employee by ID",
            description = "Updating employee by using employee ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee updated successfully"
            )
    })
    @PutMapping("/{Id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("Id") Long employeeId, @Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto employee = employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(employee);
    }

    @Operation(
            summary = "Delete Employee by ID",
            description = "Delete an employee using the employee ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee deleted successfully"
            )
    })
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("Id") Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
        return ResponseEntity.ok("Employee deleted successfully with employee id: " + employeeId);
    }
}
