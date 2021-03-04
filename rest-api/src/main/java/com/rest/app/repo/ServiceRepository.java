/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Services;

/**
 * @author danielf
 *
 */
public interface ServiceRepository extends JpaRepository<Services, Long>{

}
