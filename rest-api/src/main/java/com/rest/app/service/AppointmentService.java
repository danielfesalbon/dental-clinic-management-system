/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Appointment;
import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */
public interface AppointmentService {

	List<Useraccount> getAppointmentList();

	ResponseEntity<Map<String, Object>> saveAppointment(Appointment appointment);

	ResponseEntity<Map<String, Object>> updateAppointment(Appointment appointment);

}
