/**
 * 
 */
package com.rest.app.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Appointment;

/**
 * @author danielf
 *
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findAll(Specification<Appointment> appointment);

	List<Appointment> findByScheddateStartsWith(String scheddate);

}
