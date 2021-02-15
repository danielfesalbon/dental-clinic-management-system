/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Inventory;
import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */
public interface InventoryService {

	List<Useraccount> getInventoryList();

	ResponseEntity<Map<String, Object>> saveInventory(Inventory inventory);

	ResponseEntity<Map<String, Object>> updateInventory(Inventory inventory);

}
