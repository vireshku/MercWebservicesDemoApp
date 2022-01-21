package com.example.MercRestSample.webservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.measure.quantity.Mass;
import javax.validation.Valid;

import org.jscience.physics.amount.Amount;
import org.jscience.physics.model.RelativisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MercRestSample.exception.ResourceNotFoundException;
import com.example.MercRestSample.repository.EmployeeWebRepository;
import com.example.MercRestSample.webmodel.EmployeeWebModel;

import static javax.measure.unit.SI.KILOGRAM;

@RestController
@RequestMapping("/api/v1")
public class EmployeeWebService {

	@Autowired
	private EmployeeWebRepository employeeRepository;

	@GetMapping("/employees")
	public List<EmployeeWebModel> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@PostMapping("/employees")
	public EmployeeWebModel createEmployee(@Valid @RequestBody EmployeeWebModel employee) {
		return employeeRepository.save(employee);

	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeWebModel> getEmployeeById(@PathVariable(value = "id") Long empId)
			throws ResourceNotFoundException {

		EmployeeWebModel emp = employeeRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + empId));
		return ResponseEntity.ok().body(emp);

	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeWebModel> updateEmployee(@PathVariable(value = "id") Long empId,
			@RequestBody EmployeeWebModel employee) throws ResourceNotFoundException {
		EmployeeWebModel emp = employeeRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + empId));

		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setEmailId(employee.getEmailId());

		final EmployeeWebModel updatedEmp = employeeRepository.save(emp);
		return ResponseEntity.ok(updatedEmp);

	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long empId)
			throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		EmployeeWebModel emp = employeeRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + empId));

		employeeRepository.delete(emp);
		response.put("Deleted", Boolean.TRUE);
		return response;

	}

	@GetMapping("/confDemo")
	public Map<String, String> confDemo() {

		Map<String, String> response = new HashMap<>();
		RelativisticModel.select();
		String energy = System.getenv().get("ENERGY");

		if (energy == null) {
			energy = "72 GeV";
		}

		Amount<Mass> m = Amount.valueOf(energy).to(KILOGRAM);

		response.put("science", "E=mc^2: " + energy + " = " + m.toString());

		return response;

	}

}
