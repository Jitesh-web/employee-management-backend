package net.javaguides.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDto {
    private Long id;

    private String projectName;
}
