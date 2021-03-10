/**
 * 
 */
package com.rest.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.AppointmentService;
import com.rest.app.util.AppointmentBody;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/appointment")
@Component
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getAppointmentList(
			@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page) {
		return appointmentService.getAppointmentList(row, page);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveAppointment(@RequestBody AppointmentBody appointment) {
		return appointmentService.saveAppointment(appointment);
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateAppointment(@RequestBody AppointmentBody appointment) {
		return appointmentService.updateAppointment(appointment);
	}

	@GetMapping("/get")
	public ResponseEntity<Map<String, Object>> getAppointment(@RequestParam(required = false, name = "id") Long id) {
		return appointmentService.getAppointment(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteAppointment(@PathVariable Long id) {
		return appointmentService.deleteAppointment(id);
	}

}
