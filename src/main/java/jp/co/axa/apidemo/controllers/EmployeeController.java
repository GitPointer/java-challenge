package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.co.axa.apidemo.dto.EmployeeRequestDTO;
import jp.co.axa.apidemo.dto.EmployeeResponseDTO;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

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
        LOGGER.info("Received request to get all employees");
        List<EmployeeResponseDTO> employees = employeeService.retrieveEmployees();
        LOGGER.info("Returning {} employees", employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
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
        LOGGER.info("Received request to get employee with id {}", employeeId);
        EmployeeResponseDTO employee = employeeService.getEmployee(employeeId);
        LOGGER.info("Returning employee with id {}", employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);    }

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
        LOGGER.info("Saving employee: {}", employeeRequestDTO);
        EmployeeResponseDTO response = employeeService.saveEmployee(employeeRequestDTO);
        LOGGER.info("Saved employee: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
        LOGGER.info("Deleting employee with ID: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
        LOGGER.info("Deleted employee with ID: {}", employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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
        LOGGER.info("Updating employee with ID: {} with data: {}", employeeId, employeeRequestDTO);
        EmployeeResponseDTO emp = employeeService.getEmployee(employeeId);
        LOGGER.info("Retrieved employee to update: {}", emp);
        EmployeeResponseDTO updatedEmp = employeeService.updateEmployee(employeeRequestDTO, employeeId);
        LOGGER.info("Updated employee: {}", updatedEmp);
        return new ResponseEntity<>(updatedEmp, HttpStatus.OK);
    }

}
