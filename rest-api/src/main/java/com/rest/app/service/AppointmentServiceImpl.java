/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.table.Appointment;
import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */
@Component
public class AppointmentServiceImpl implements AppointmentService {

	@Override
	public List<Useraccount> getAppointmentList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		return null;
	}

}
