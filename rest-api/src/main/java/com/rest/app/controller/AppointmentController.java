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
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.AppointmentService;
import com.rest.app.table.Appointment;
import com.rest.app.table.Useraccount;

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
	public List<Useraccount> getAppointmentList() {
		return appointmentService.getAppointmentList();
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveAppointment(@RequestBody Appointment appointment) {
		return appointmentService.saveAppointment(appointment);
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateAppointment(@RequestBody Appointment appointment) {
		return appointmentService.updateAppointment(appointment);
	}

}
