package net.javaguides.ems.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50,
            message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50,
            message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address")
    private String email;
}
