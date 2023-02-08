package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeRequestDTO;
import jp.co.axa.apidemo.dto.EmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.excpetions.ApiRuntimeException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Cacheable(value = "employees")
    public List<EmployeeResponseDTO> retrieveEmployees() {
        LOGGER.info("Retrieving all employees from the database.");
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeResponseDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeResponseDTOS.add(modelMapper.map(employee, EmployeeResponseDTO.class)));
        LOGGER.info("Retrieved all employees from the database.");
        return employeeResponseDTOS;
    }
    @Cacheable(cacheNames = "employees", key = "#employeeId")
    public EmployeeResponseDTO getEmployee(Long employeeId) {
        LOGGER.info("Retrieving employee with id {} from the database.", employeeId);
        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
        if (!optEmployee.isPresent()) {
            LOGGER.error("Employee with id {} not found.", employeeId);
            throw new ApiRuntimeException("Employee with id " + employeeId + " not found");
        }
        Employee employee = optEmployee.get();
        LOGGER.info("Retrieved employee with id {} from the database.", employeeId);
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO){
        LOGGER.info("Saving employee with name {} to the database.", employeeRequestDTO.getName());
        Employee employee = modelMapper.map(employeeRequestDTO, Employee.class);
        employee = employeeRepository.save(employee);
        LOGGER.info("Saved employee with name {} to the database.", employeeRequestDTO.getName());
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    @CacheEvict(cacheNames = "employees", key = "#employeeId", allEntries = false)
    public void deleteEmployee(Long employeeId){
        LOGGER.info("Deleting employee with id {} from the database.", employeeId);
        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
        if (!optEmployee.isPresent()) {
            LOGGER.error("Employee not found for this id :: {}.", employeeId);
            throw new ApiRuntimeException("Employee not found for this id :: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
        LOGGER.info("Deleted employee with id {} from the database.", employeeId);
    }

    @CachePut(cacheNames = "employees", key = "#employeeId")
    public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO, Long employeeId) {
        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
        if (!optEmployee.isPresent()) {
            LOGGER.error("Employee with id {} not found", employeeId);
            throw new ApiRuntimeException("Employee with id " + employeeId + " not found");
        }
        Employee employee = optEmployee.get();
        employee.setName(employeeRequestDTO.getName());
        employee.setSalary(employeeRequestDTO.getSalary());
        employee.setDepartment(employeeRequestDTO.getDepartment());
        employeeRepository.save(employee);
        LOGGER.info("Employee with id {} updated", employeeId);
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }
}