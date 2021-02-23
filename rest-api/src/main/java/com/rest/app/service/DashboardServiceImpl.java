/**
 * 
 */
package com.rest.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.AppointmentRepository;
import com.rest.app.repo.PatientRepository;
import com.rest.app.repo.TransactionRepository;
import com.rest.app.repo.UseraccountRepository;
import com.rest.app.table.Appointment;
import com.rest.app.table.Transaction;
import com.rest.app.util.FullCalendarEvent;
import com.rest.app.util.ScheduleUtil;

/**
 * @author danielf
 *
 */
@Component
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private TransactionRepository txRepository;
	@Autowired
	private UseraccountRepository userRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private ScheduleUtil schedUtil;

	@SuppressWarnings("static-access")
	@Override
	public ResponseEntity<Map<String, Object>> getDashboard(String monthDate) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			List<FullCalendarEvent> schedules = new ArrayList<FullCalendarEvent>();
			monthDate = monthDate != null ? monthDate : new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
			Appointment app = new Appointment();
			app.setScheddate(dateFormat.format(new Date()));
			Example<Appointment> appexample = Example.of(app);

			Transaction tx = new Transaction();
			tx.setTransactiondate(new Date());
			Example<Transaction> txexample = Example.of(tx);

			Date monthdate = new SimpleDateFormat("MM/dd/yyyy").parse(monthDate);
			String monthString = schedUtil.getYearMonthString(monthdate);
			// Date[] range = schedUtil.getMonthInterval(monthdate);
			List<Appointment> list = appointmentRepository.findByScheddateStartsWith(monthString);

//			List<Appointment> list = appointmentRepository
//					.findAll(schedUtil.getSpecFromDatesAndExample(range[0], schedUtil.addDays(range[1], 1), null));
//
//			String[] ids = TimeZone.getAvailableIDs();
//			for (int i = 0; i < ids.length; i++) {
//				System.out.println(ids[i]);
//			}
			for (Appointment a : list) {
				FullCalendarEvent sched = new FullCalendarEvent();
				Date time = timeFormat.parse(a.getSchedtime());
				Calendar timeCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
				timeCal.setTime(time);
				Calendar dateCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
				dateCal.setTime(dateFormat.parse(a.getScheddate()));
				dateCal.set(Calendar.HOUR, (timeCal.get(Calendar.HOUR) + 4));// Asia/Pacific - need hour adjustment
				dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
				sched.setStart(dateCal.getTime());
				sched.setId(a.getId());
				sched.setTitle(
						a.getPtid() + ", " + a.getPtfirstname().charAt(0) + ". " + a.getPtlastname().charAt(0) + ".");
				dateCal.add(Calendar.HOUR, 1);
				sched.setEnd(dateCal.getTime());
				sched.setBackgroundColor("");
				sched.setTextColor("");
				if (a.getDone()) {
					sched.setBackgroundColor("#68ee68");
				}
				schedules.add(sched);
			}

			response.put("schedules", schedules);
			response.put("patient", patientRepository.count());
			response.put("schedcount", appointmentRepository.count(appexample));
			response.put("txcount", txRepository.count(txexample));
			response.put("saveduser", userRepository.count());

		} catch (Exception e) {
			e.printStackTrace();
			response.put("patient", 0);
			response.put("schedcount", 0);
			response.put("txcount", 0);
			response.put("saveduser", 0);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}
