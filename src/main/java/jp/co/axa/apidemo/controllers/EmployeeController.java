package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dto.EmployeeRequestDTO;
import jp.co.axa.apidemo.dto.EmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<EmployeeResponseDTO> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public EmployeeResponseDTO getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public EmployeeResponseDTO saveEmployee(@RequestBody @Valid EmployeeRequestDTO employeeDTO){
        return employeeService.saveEmployee(employeeDTO);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody @Valid EmployeeRequestDTO employeeDTO,
                               @PathVariable(name="employeeId")Long employeeId){
        EmployeeResponseDTO emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            employeeService.updateEmployee(employeeDTO,employeeId);
        }

    }

}
