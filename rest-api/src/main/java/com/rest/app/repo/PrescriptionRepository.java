/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Prescription;

/**
 * @author danielf
 *
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
