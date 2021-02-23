/**
 * 
 */
package com.rest.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.InventoryService;
import com.rest.app.table.Inventory;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/inventory")
@Component
public class InventoryController {

	@Autowired
	private InventoryService inventorytService;

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getInventoryList(
			@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page) {
		return inventorytService.getInventoryList(row, page);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveInventory(@RequestBody Inventory inventory) {
		return inventorytService.saveInventory(inventory);
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateInventory(@RequestBody Inventory inventory) {
		return inventorytService.updateInventory(inventory);
	}

	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> searchInventory(
			@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page,
			@RequestParam(required = false, name = "name") String name) {
		return inventorytService.searchInventory(row, page, name);
	}
	
	@GetMapping("/get")
	public ResponseEntity<Map<String, Object>> getInventory(@RequestParam(required = false, name = "id") Long id) {
		return inventorytService.getInventory(id);
	}
}
