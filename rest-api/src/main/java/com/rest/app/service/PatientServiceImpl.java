/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.table.Patient;
import com.rest.app.util.PatientDetails;

/**
 * @author danielf
 *
 */
@Component
public class PatientServiceImpl implements PatientService {

	@Override
	public List<Patient> getPatientList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> savePatient(Patient patient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> updatePatient(Patient patient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<PatientDetails> getPatient(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
