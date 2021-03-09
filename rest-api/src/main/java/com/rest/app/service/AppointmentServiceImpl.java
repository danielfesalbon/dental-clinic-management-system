/**
 * 
 */
package com.rest.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.rest.app.table.Appointment;
import com.rest.app.util.AppointmentBody;
import com.rest.app.util.PaginationUtil;
import com.rest.app.util.ScheduleUtil;

/**
 * @author danielf
 *
 */
@Component
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private PaginationUtil pageUtil;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private ScheduleUtil schedUtil;

	@Override
	public ResponseEntity<Map<String, Object>> getAppointmentList(Integer row, Integer page) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			Appointment app = new Appointment();
			app.setDone(false);
			Example<Appointment> example = Example.of(app);
			response.put("pagevalues", pageUtil.getPageValues(row, (int) appointmentRepository.count()));
			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "id"));
			List<Appointment> list = appointmentRepository.findAll(example, pagination).getContent();
			response.put("appoinments", list);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings("static-access")
	@Override
	public ResponseEntity<Map<String, Object>> saveAppointment(AppointmentBody appointment) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			// Time time = schedUtil.getTimeOf(appointment.getSchedtime());
			Appointment app = new Appointment();
			app.setService(appointment.getService());
			app.setDatecreated(new Date());
			app.setPtid(appointment.getPtid());
			app.setPtfirstname(appointment.getPtfirstname().toUpperCase());
			app.setPtlastname(appointment.getPtlastname().toUpperCase());
			app.setPtaddress(appointment.getPtaddress());
			app.setPtcontact(appointment.getPtcontact());
			app.setRemarks(appointment.getRemarks());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			app.setScheddate(dateFormat.format(schedUtil.addDays(appointment.getScheddate(), 1)));
			app.setSchedtime(appointment.getSchedtime());
			app.setDone(false);
			response.put("event", "Saved new appointment schedule");
			Appointment existing = new Appointment();
			// existing.setScheddate(app.getScheddate());
			existing.setSchedtime(app.getSchedtime());
			existing.setScheddate(app.getScheddate());
			existing.setDone(app.getDone());
			Example<Appointment> example = Example.of(existing);
			if (appointmentRepository.count(example) > 0) {
				response.put("event", "Selected time schedule occupied already");
				response.put("flag", "failed");
				return ResponseEntity.badRequest().body(response);
			} else {
				appointmentRepository.save(app);
				response.put("event", "Saved new appointment schedule: " + app.getId());
				response.put("flag", "success");
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
	public ResponseEntity<Map<String, Object>> updateAppointment(AppointmentBody appointment) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			if (appointmentRepository.existsById(appointment.getId())) {
				Appointment a = appointmentRepository.findById(appointment.getId()).get();
				a.setSchedtime(appointment.getSchedtime());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				a.setScheddate(dateFormat.format(appointment.getScheddate()));
				a.setDoctor(appointment.getDoctor());
				a.setService(appointment.getService());
				a.setRemarks(appointment.getRemarks());
				a.setDone(appointment.isDone());
				Appointment existing = new Appointment();
				existing.setScheddate(a.getScheddate());
				existing.setSchedtime(a.getSchedtime());
				existing.setDone(false);
				Example<Appointment> example = Example.of(existing);
				if (appointmentRepository.findOne(example).isPresent()
						&& !appointmentRepository.findOne(example).get().getId().equals(a.getId())) {
					response.put("event", "Selected time schedule occupied already");
					response.put("flag", "failed");
					return ResponseEntity.badRequest().body(response);
				} else {
					appointmentRepository.save(a);
					response.put("event", "Update appointment: " + a.getId());
					response.put("flag", "success");
				}
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
	public ResponseEntity<Map<String, Object>> getAppointment(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			if (appointmentRepository.existsById(id)) {
				response.put("appointment", appointmentRepository.findById(id).get());
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
