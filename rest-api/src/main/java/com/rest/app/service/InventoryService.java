/**
 * 
 */
package com.rest.app.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Inventory;

/**
 * @author danielf
 *
 */
public interface InventoryService {

	ResponseEntity<Map<String, Object>> getInventoryList(Integer row, Integer page);

	ResponseEntity<Map<String, Object>> saveInventory(Inventory inventory);

	ResponseEntity<Map<String, Object>> updateInventory(Inventory inventory);

	ResponseEntity<Map<String, Object>> searchInventory(Integer row, Integer page, String name);

	ResponseEntity<Map<String, Object>> getInventory(Long id);

}
