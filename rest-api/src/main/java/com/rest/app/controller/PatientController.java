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
import com.rest.app.table.Prescription;

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
	public ResponseEntity<Map<String, Object>> getPatientList(@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page) {
		return patientService.getPatientList(row, page);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> savePatient(@RequestBody Patient patient) {
		return patientService.savePatient(patient);
	}

	@PostMapping("/upload")
	public ResponseEntity<Map<String, Object>> uploadPatient(@RequestBody List<Patient> patient) {
		return patientService.uploadPatient(patient);
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updatePatient(@RequestBody Patient patient) {
		return patientService.updatePatient(patient);
	}

	@GetMapping("/get")
	public ResponseEntity<Map<String, Object>> getPatient(@RequestParam(required = false, name = "id") Long id,
			@RequestParam(required = false, name = "approw") Integer approw,
			@RequestParam(required = false, name = "apppage") Integer apppage,
			@RequestParam(required = false, name = "txrow") Integer txrow,
			@RequestParam(required = false, name = "txpage") Integer txpage,
			@RequestParam(required = false, name = "prescrow") Integer prescrow,
			@RequestParam(required = false, name = "prescpage") Integer prescpage) {
		return patientService.getPatient(id, approw, apppage, txrow, txpage, prescrow, prescpage);
	}

	@GetMapping("/get/public")
	public ResponseEntity<Map<String, Object>> getPatientByName(@RequestParam(required = false, name = "id") Long id,
			@RequestParam(required = false, name = "firstname") String firstname,
			@RequestParam(required = false, name = "lastname") String lastname) {
		return patientService.getPatientByName(id, firstname, lastname);
	}

	@PostMapping("/prescription/save")
	public ResponseEntity<Map<String, Object>> makePrescription(@RequestBody Prescription prescription) {
		return patientService.makePrescription(prescription);
	}

}
