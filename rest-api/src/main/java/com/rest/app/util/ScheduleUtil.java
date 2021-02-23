/**
 * 
 */
package com.rest.app.util;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.rest.app.table.Appointment;

/**
 * @author danielf
 *
 */
@Component
public class ScheduleUtil {

	public Specification<Appointment> getSpecFromDatesAndExample(Date from, Date to, Example<Appointment> example) {
		return (Specification<Appointment>) (root, query, builder) -> {
			final List<Predicate> predicates = new ArrayList<>();

			if (from != null) {
				predicates.add(builder.greaterThan(root.get("scheddate"), from));
			}
			if (to != null) {
				predicates.add(builder.lessThan(root.get("scheddate"), to));
			}
			if (example != null) {
				predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
			}

			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

	public static Date[] getMonthInterval(Date data) throws Exception {

		Date[] dates = new Date[2];

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		start.setTime(data);
		start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);

		end.setTime(data);
		end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);

		// System.out.println("start "+ start.getTime());
		// System.out.println("end "+ end.getTime());

		dates[0] = start.getTime();
		dates[1] = end.getTime();

		return dates;
	}

	public static String getYearMonthString(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String mnth = String.valueOf((cal.get(Calendar.MONTH) + 1));
		mnth = mnth.length() < 2 ? "0" + mnth : mnth;
		return cal.get(Calendar.YEAR) + "-" + mnth + "-";
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public static Time getTimeOf(Date date) {
		LocalDateTime datetime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalTime now = datetime.toLocalTime();
		return Time.valueOf(now);
	}

	public static Comparator<FullCalendarEvent> sortBySchedule = new Comparator<FullCalendarEvent>() {
		public int compare(FullCalendarEvent time1, FullCalendarEvent time2) {
			return time1.getStart().compareTo(time2.getStart());
		}
	};

}
