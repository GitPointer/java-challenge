package jp.co.axa.apidemo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class EmployeeRequestDTO {

    @NotBlank(message = "Employee name cannot be empty")
    private String name;

    @Min(value = 0, message = "Salary must be greater than or equal to 0")
    private Integer salary;

    @NotBlank(message = "Department name cannot be empty")
    private String department;
}
