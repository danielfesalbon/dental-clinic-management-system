/**
 * 
 */
package com.rest.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.InventoryRepository;
import com.rest.app.table.Inventory;
import com.rest.app.util.PaginationUtil;

/**
 * @author danielf
 *
 */
@Component
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private PaginationUtil pageUtil;

	@Override
	public ResponseEntity<Map<String, Object>> getInventoryList(Integer row, Integer page) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			response.put("pagevalues", pageUtil.getPageValues(row, (int) inventoryRepository.count()));
			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "id"));
			response.put("inventory", inventoryRepository.findAll(pagination).getContent());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Inventory i = new Inventory();
			i.setCreatedby(inventory.getCreatedby());
			i.setDatecreated(new Date());
			i.setDescription(inventory.getDescription());
			i.setName(inventory.getName());
			i.setUnitprice(inventory.getUnitprice());
			i.setItemcode(inventory.getItemcode());
			i.setQuantity(inventory.getQuantity());
			i.setType(inventory.getType());
			i.setSupplier(inventory.getSupplier());
			inventoryRepository.save(i);
			response.put("event",
					"Saved new clinic item: " + i.getId() + ". " + i.getName() + ". Type: " + i.getType());
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			if (inventoryRepository.existsById(inventory.getId())) {
				Inventory i = inventoryRepository.findById(inventory.getId()).get();
				i.setLastupdated(new Date());
				i.setUpdatedby(inventory.getUpdatedby());
				i.setDescription(inventory.getDescription());
				i.setType(inventory.getType());
				i.setQuantity(inventory.getQuantity());
				i.setDescription(inventory.getDescription());
				i.setUnitprice(inventory.getUnitprice());
				inventoryRepository.save(i);
				response.put("event", "Update clinic item details: " + i.getId());
				response.put("flag", "success");

			} else {
				response.put("flag", "failed");
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchInventory(Integer row, Integer page, String name) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "id"));
			List<Inventory> list = inventoryRepository.findByNameContainingIgnoreCase(name, pagination);
			response.put("pagevalues", pageUtil.getPageValues(row, list.size()));
			response.put("list", list);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getInventory(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			if (inventoryRepository.existsById(id)) {
				response.put("inventory", inventoryRepository.findById(id).get());
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}
