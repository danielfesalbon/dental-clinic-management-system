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

import com.rest.app.service.DentalService;
import com.rest.app.table.Dental;
import com.rest.app.table.Services;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/service")
@Component
public class ServiceController {

	@Autowired
	private DentalService dentalService;

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getServiceList(@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page) {
		return dentalService.getServiceList(row, page);
	}
	
	@GetMapping("/list/all")
	public ResponseEntity<Map<String, Object>> getServiceListAll() {
		return dentalService.getServiceListAll();
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveService(@RequestBody Services service) {
		return dentalService.saveService(service);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteService(@PathVariable Long id) {
		return dentalService.deleteService(id);
	}

	@GetMapping("/dental-details")
	public ResponseEntity<Map<String, Object>> getDentalDetails() {
		return dentalService.getDentalDetails();
	}

	@PutMapping("/dental/update")
	public ResponseEntity<Map<String, Object>> updateDental(@RequestBody Dental dental) {
		return dentalService.updateDental(dental);
	}

}
