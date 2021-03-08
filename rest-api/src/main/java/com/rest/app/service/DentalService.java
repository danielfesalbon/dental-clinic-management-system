/**
 * 
 */
package com.rest.app.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Dental;
import com.rest.app.table.Services;

/**
 * @author danielf
 *
 */
public interface DentalService {

	ResponseEntity<Map<String, Object>> getServiceList(Integer row, Integer page);

	ResponseEntity<Map<String, Object>> saveService(Services service);

	ResponseEntity<Map<String, Object>> deleteService(Long id);

	ResponseEntity<Map<String, Object>> getDentalDetails();

	ResponseEntity<Map<String, Object>> updateDental(Dental dental);

	ResponseEntity<Map<String, Object>> getServiceListAll();

}
