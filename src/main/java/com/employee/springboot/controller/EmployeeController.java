package com.employee.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.employee.springboot.payload.EmployeeDto;
import com.employee.springboot.service.EmployeeService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200/**")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/saveEmployee/{departmentId}/{companyId}")
	public ResponseEntity<EmployeeDto> saveEmployee(@PathVariable("departmentId") Long departmentId
			                                    ,@PathVariable("companyId") Long companyId
			                                     ,@RequestBody EmployeeDto employeeDto) {
		
		return new ResponseEntity<>(
				employeeService.saveEmployee(departmentId,companyId,employeeDto)
				,HttpStatus.CREATED);
	
	}
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
		
		return new ResponseEntity<>(employeeService.getAllEmployees(),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
	@GetMapping("/getEmployeeById/{id}")
	public ResponseEntity<EmployeeDto>  getEmployeeById(@PathVariable("id") Long employeeId){	
		return new ResponseEntity<EmployeeDto>( employeeService.getEmployeeById(employeeId),HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
	@PutMapping("/updateEmployeeById/{id}")
	public ResponseEntity<EmployeeDto>  updateEmployeeById(@RequestBody EmployeeDto employeeDto
			                                                ,@PathVariable("id") Long employeeId) {
		return new ResponseEntity<EmployeeDto>( employeeService.updateEmployeeById(employeeDto,employeeId)
				                                       ,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
	@DeleteMapping("/deleteEmployeeById/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployeeById(@PathVariable("id") Long employeeId) {
		
		employeeService.deleteEmployeeById(employeeId);
		
		Map<String,Boolean> response = new HashMap<>();
		response.put("Deleted",Boolean.TRUE);
	
		return ResponseEntity.ok(response);
	}
	
	
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
	@GetMapping("/getEmployeesByCompanyId/{companyId}")
	public ResponseEntity<List<EmployeeDto>> getEmployeesByCompanyId(@PathVariable("companyId") Long companyId){
	
		return new ResponseEntity<List<EmployeeDto>>(employeeService.getEmployeeByCompanyId(companyId)
													,HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/getSal")
	public ResponseEntity<List<EmployeeDto>> getSalBySort(){
		List<EmployeeDto> employee = employeeService.getSalaryBySort();
		return new ResponseEntity<List<EmployeeDto>>(employee,HttpStatus.OK);
	}

}
