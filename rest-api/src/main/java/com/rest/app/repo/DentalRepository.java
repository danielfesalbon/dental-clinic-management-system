/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Dental;

/**
 * @author danielf
 *
 */
public interface DentalRepository extends JpaRepository<Dental, Long> {

}
