/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.table.Inventory;
import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */
@Component
public class InventoryServiceImpl implements InventoryService {

	@Override
	public List<Useraccount> getInventoryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		return null;
	}

}
