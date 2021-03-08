/**
 * 
 */
package com.rest.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.AppointmentRepository;
import com.rest.app.repo.InvoiceRepository;
import com.rest.app.repo.PatientRepository;
import com.rest.app.repo.PrescriptionRepository;
import com.rest.app.table.Appointment;
import com.rest.app.table.Invoice;
import com.rest.app.table.Patient;
import com.rest.app.table.Prescription;
import com.rest.app.util.PaginationUtil;

/**
 * @author danielf
 *
 */
@Component
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PaginationUtil pageUtil;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Override
	public ResponseEntity<Map<String, Object>> getPatientList(Integer row, Integer page) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			response.put("pagevalues", pageUtil.getPageValues(row, (int) patientRepository.count()));
			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "id"));
			response.put("patient", patientRepository.findAll(pagination).getContent());
			// return patientRepository.findAll();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> savePatient(Patient patient) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("event", "Saved new patient record");
			patient.setFirstname(patient.getFirstname() != null ? patient.getFirstname().toUpperCase() : null);
			patient.setLastname(patient.getLastname() != null ? patient.getLastname().toUpperCase() : null);
			patient.setMiddlename(patient.getMiddlename() != null ? patient.getMiddlename().toUpperCase() : null);
			patient.setDatecreated(new Date());
			patientRepository.save(patient);
			response.put("event", "Saved new patient record: " + patient.getId());
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);

	}

	@Override
	public ResponseEntity<Map<String, Object>> updatePatient(Patient patient) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("event", "Update Patient " + patient.getId() + "'s record");
			if (patientRepository.existsById(patient.getId())) {
				Patient p = patientRepository.findById(patient.getId()).get();
				p.setAddress(patient.getAddress());
				p.setContact(patient.getContact());
				p.setEmail(patient.getEmail());
				p.setGender(patient.getGender());
				p.setMiddlename(patient.getMiddlename().toUpperCase());
				patientRepository.save(p);
				response.put("flag", "success");

			} else {
				response.put("flag", "failed");
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getPatient(Long id, Integer approw, Integer apppage, Integer txrow,
			Integer txpage, Integer prescrow, Integer prescpage) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			if (patientRepository.existsById(id)) {
				response.put("patient", patientRepository.findById(id).get());

				Appointment app = new Appointment();
				Invoice inv = new Invoice();
				Prescription pr = new Prescription();

				app.setPtid(id);
				inv.setPtid(id);
				pr.setPtid(id);

				Example<Appointment> aexample = Example.of(app);
				Example<Invoice> iexample = Example.of(inv);
				Example<Prescription> pexample = Example.of(pr);

				response.put("apppage", pageUtil.getPageValues(approw, (int) appointmentRepository.count(aexample)));
				response.put("invpage", pageUtil.getPageValues(txrow, (int) invoiceRepository.count(iexample)));
				response.put("prpage", pageUtil.getPageValues(prescrow, (int) prescriptionRepository.count(pexample)));

				Pageable apppagination = PageRequest.of(apppage, approw, Sort.by(Sort.Direction.DESC, "id"));
				Pageable txpagination = PageRequest.of(txpage, txrow, Sort.by(Sort.Direction.DESC, "id"));
				Pageable prpagination = PageRequest.of(prescpage, prescrow, Sort.by(Sort.Direction.DESC, "id"));

				response.put("appointments", appointmentRepository.findAll(aexample, apppagination).getContent());
				response.put("invoices", invoiceRepository.findAll(iexample, txpagination).getContent());
				response.put("prescriptions", prescriptionRepository.findAll(pexample, prpagination).getContent());

			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> makePrescription(Prescription prescription) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();

		try {

			prescriptionRepository.save(prescription);
			response.put("event", "Saved prescription: " + prescription.getId());
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> uploadPatient(List<Patient> patient) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("event", "Saved new patient record");
			for (Patient p : patient) {
				p.setFirstname(p.getFirstname() != null ? p.getFirstname().toUpperCase() : null);
				p.setLastname(p.getLastname() != null ? p.getLastname().toUpperCase() : null);
				p.setMiddlename(p.getMiddlename() != null ? p.getMiddlename().toUpperCase() : null);
				p.setDatecreated(new Date());
			}
			patientRepository.saveAll(patient);
			response.put("event", "Uploaded patient record. Total record: " + patient.size());
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getPatientByName(Long id, String firstname, String lastname) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			if (patientRepository.existsById(id)) {
				Patient p = patientRepository.findById(id).get();
			
				if (p.getFirstname().toUpperCase().equals(firstname.toUpperCase())
						&& p.getLastname().toUpperCase().equals(lastname.toUpperCase())) {
					response.put("patient", p);
				} else {
					response.put("message", "Invalid Credentials");
					return ResponseEntity.badRequest().body(response);
				}
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}
