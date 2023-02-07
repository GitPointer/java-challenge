package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.co.axa.apidemo.dto.EmployeeRequestDTO;
import jp.co.axa.apidemo.dto.EmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api( tags = {"Employee Service"})
@Tag(name = "Employee Service", description = "These services manage employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiOperation(value = "Get all employees"
            , produces = MediaType.APPLICATION_JSON_VALUE
            , httpMethod = "GET"
            ,notes = "Get all employees in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        return new ResponseEntity<>(employeeService.retrieveEmployees(), HttpStatus.OK);

    }

    @GetMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiOperation(value = "Get employee by id"
            , produces = MediaType.APPLICATION_JSON_VALUE
            , httpMethod = "GET"
            ,notes = "Get employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Employee not found")
    })
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    @PostMapping("/employees")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create a new employee"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , httpMethod = "POST"
            , notes = "Create a new employee in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<EmployeeResponseDTO> saveEmployee(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO){
        return new ResponseEntity<>(employeeService.saveEmployee(employeeRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete employee by id"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , httpMethod = "DELETE"
            , notes = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 401, message = "Not authorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Employee not found")
    })
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);    }

    @PutMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update employee by id"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , httpMethod = "PUT"
            , notes = "Update employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful Modification"),
            @ApiResponse(code = 401, message = "Not authorized to perform this operation"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Employee not found")
    })
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO,
                               @PathVariable(name="employeeId")Long employeeId){
        EmployeeResponseDTO emp = employeeService.getEmployee(employeeId);
        return new ResponseEntity<>(employeeService.updateEmployee(employeeRequestDTO, employeeId), HttpStatus.OK);
    }

}
