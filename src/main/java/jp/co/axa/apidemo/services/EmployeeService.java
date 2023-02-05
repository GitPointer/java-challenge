package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeRequestDTO;
import jp.co.axa.apidemo.dto.EmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeResponseDTO> retrieveEmployees();

    public EmployeeResponseDTO getEmployee(Long employeeId);

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeDTO);

    public void deleteEmployee(Long employeeId);

    public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeDTO, Long employeeId);
}