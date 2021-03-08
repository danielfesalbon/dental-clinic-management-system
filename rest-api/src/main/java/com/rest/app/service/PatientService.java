/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Patient;
import com.rest.app.table.Prescription;

/**
 * @author danielf
 *
 */
public interface PatientService {

	ResponseEntity<Map<String, Object>> getPatientList(Integer row, Integer page);

	ResponseEntity<Map<String, Object>> savePatient(Patient patient);

	ResponseEntity<Map<String, Object>> updatePatient(Patient patient);

	ResponseEntity<Map<String, Object>> getPatient(Long id, Integer approw, Integer apppage, Integer txrow, Integer txpage, Integer prescrow, Integer prescpage);

	ResponseEntity<Map<String, Object>> makePrescription(Prescription prescription);

	ResponseEntity<Map<String, Object>> uploadPatient(List<Patient> patient);

	ResponseEntity<Map<String, Object>> getPatientByName(Long id, String firstname, String lastname);

}
