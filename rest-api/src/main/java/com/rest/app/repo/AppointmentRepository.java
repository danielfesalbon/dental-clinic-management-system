/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Appointment;

/**
 * @author danielf
 *
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
