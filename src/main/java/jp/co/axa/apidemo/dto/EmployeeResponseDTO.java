package jp.co.axa.apidemo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String salary;

    private String department;
}