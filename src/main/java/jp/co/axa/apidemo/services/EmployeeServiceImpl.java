package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeRequestDTO;
import jp.co.axa.apidemo.dto.EmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.excpetions.ApiRuntimeException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<EmployeeResponseDTO> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeResponseDTOS.add(modelMapper.map(employee, EmployeeResponseDTO.class)));
        return employeeResponseDTOS;
    }

    public EmployeeResponseDTO getEmployee(Long employeeId) {
        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
        if (!optEmployee.isPresent()) {
            throw new ApiRuntimeException("Employee with id " + employeeId + " not found");
        }
        Employee employee = optEmployee.get();
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO){
        Employee employee = modelMapper.map(employeeRequestDTO, Employee.class);
        employee = employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    public void deleteEmployee(Long employeeId){
        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
        if (!optEmployee.isPresent()) {
            throw new ApiRuntimeException("Employee not found for this id :: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

    public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO, Long employeeId) {
        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
        if (!optEmployee.isPresent()) {
            throw new ApiRuntimeException("Employee with id " + employeeId + " not found");
        }
        Employee employee = optEmployee.get();
        employee.setName(employeeRequestDTO.getName());
        employee.setSalary(employeeRequestDTO.getSalary());
        employee.setDepartment(employeeRequestDTO.getDepartment());
        employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }
}