package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dto.EmployeeRequestDTO;
import jp.co.axa.apidemo.dto.EmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        return new ResponseEntity<>(employeeService.retrieveEmployees(), HttpStatus.OK);

    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponseDTO> saveEmployee(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO){
        return new ResponseEntity<>(employeeService.saveEmployee(employeeRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO,
                               @PathVariable(name="employeeId")Long employeeId){
        EmployeeResponseDTO emp = employeeService.getEmployee(employeeId);
        return new ResponseEntity<>(employeeService.updateEmployee(employeeRequestDTO, employeeId), HttpStatus.OK);
    }

}
