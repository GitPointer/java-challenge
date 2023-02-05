package jp.co.axa.apidemo.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO {

    private Long id;

    private String name;

    private String salary;

    private String department;
}