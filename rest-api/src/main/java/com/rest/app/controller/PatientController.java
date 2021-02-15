/**
 * 
 */
package com.rest.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.PatientService;
import com.rest.app.table.Patient;
import com.rest.app.util.PatientDetails;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/patient")
@Component
public class PatientController {

	@Autowired
	private PatientService patientService;

	@GetMapping("/list")
	public List<Patient> getPatientList() {
		return patientService.getPatientList();
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> savePatient(@RequestBody Patient patient) {
		return patientService.savePatient(patient);
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updatePatient(@RequestBody Patient patient) {
		return patientService.updatePatient(patient);
	}

	@GetMapping("/get")
	public ResponseEntity<PatientDetails> getPatient(@RequestParam(required = false, name = "id") Long id) {
		return patientService.getPatient(id);
	}

}
