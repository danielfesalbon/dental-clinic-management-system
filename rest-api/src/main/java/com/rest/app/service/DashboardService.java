/**
 * 
 */
package com.rest.app.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

/**
 * @author danielf
 *
 */
public interface DashboardService {

	ResponseEntity<Map<String, Object>> getDashboard(String monthDate);

}
