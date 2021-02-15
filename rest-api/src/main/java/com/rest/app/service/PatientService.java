/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Patient;
import com.rest.app.util.PatientDetails;

/**
 * @author danielf
 *
 */
public interface PatientService {

	List<Patient> getPatientList();

	ResponseEntity<Map<String, Object>> savePatient(Patient patient);

	ResponseEntity<Map<String, Object>> updatePatient(Patient patient);

	ResponseEntity<PatientDetails> getPatient(Long id);

}
