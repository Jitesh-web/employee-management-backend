package net.javaguides.ems.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetails {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> messages;
    private String path;
}
