package net.javaguides.ems.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedQueries;

import java.util.List;

@NamedQueries({

        @NamedQuery(
                name = "Employee.searchEmployees",
                query = """
                    SELECT e
                    FROM Employee e
                    WHERE LOWER(e.firstName)
                    LIKE LOWER(CONCAT('%', :keyword, '%'))
                    """
        ),

        @NamedQuery(
                name = "Employee.filterEmployees",
                query = """
                    SELECT e
                    FROM Employee e
                    WHERE LOWER(e.department)
                    = LOWER(:department)
                    """
        ),

        @NamedQuery(
                name = "Employee.searchAndFilterEmployees",
                query = """
                    SELECT e
                    FROM Employee e
                    WHERE LOWER(e.firstName)
                    LIKE LOWER(CONCAT('%', :keyword, '%'))
                    AND LOWER(e.department)
                    = LOWER(:department)
                    """
        )
})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id", nullable = false, unique = true)
    private String email;

    @Column(name = "department")
    private String department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Project> projects;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "employee_skills",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonManagedReference
    private List<Skill> skills;
}
