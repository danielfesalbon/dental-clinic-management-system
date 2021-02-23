/**
 * 
 */
package com.rest.app.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.util.AppointmentBody;

/**
 * @author danielf
 *
 */
public interface AppointmentService {

	ResponseEntity<Map<String, Object>> getAppointmentList(Integer row, Integer page);

	ResponseEntity<Map<String, Object>> saveAppointment(AppointmentBody appointment);

	ResponseEntity<Map<String, Object>> updateAppointment(AppointmentBody appointment);

	ResponseEntity<Map<String, Object>> getAppointment(Long id);

}
