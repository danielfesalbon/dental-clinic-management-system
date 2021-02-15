/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Patient;

/**
 * @author danielf
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
