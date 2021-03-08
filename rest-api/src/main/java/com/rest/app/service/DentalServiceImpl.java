/**
 * 
 */
package com.rest.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.DentalRepository;
import com.rest.app.repo.ServiceRepository;
import com.rest.app.table.Dental;
import com.rest.app.table.Services;
import com.rest.app.util.PaginationUtil;

/**
 * @author danielf
 *
 */
@Component
public class DentalServiceImpl implements DentalService {

	@Autowired
	private DentalRepository dentalRepository;
	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private PaginationUtil pageUtil;

	@Override
	public ResponseEntity<Map<String, Object>> getServiceList(Integer row, Integer page) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			response.put("pagevalues", pageUtil.getPageValues(row, (int) serviceRepository.count()));
			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "id"));
			response.put("services", serviceRepository.findAll(pagination).getContent());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveService(Services service) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("event", "Saved new service");
			service.setDateadded(new Date());
			service.setName(service.getName().toUpperCase());
			serviceRepository.save(service);
			response.put("event", "Saved new service: " + service.getName());
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<Map<String, Object>> deleteService(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			Services s = serviceRepository.findById(id).get();
			if (s != null) {
				serviceRepository.delete(s);
				response.put("event", "Service " + s.getName() + " deleted");
				response.put("flag", "success");
			} else {
				response.put("flag", "failed");
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
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
	public ResponseEntity<Map<String, Object>> getDentalDetails() {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();

		try {

			response.put("details", dentalRepository.findById((long) 1));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);

	}

	@Override
	public ResponseEntity<Map<String, Object>> updateDental(Dental dental) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			response.put("event", "Update clinic details");
			dentalRepository.save(dental);
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
	public ResponseEntity<Map<String, Object>> getServiceListAll() {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			response.put("services", serviceRepository.findAll());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}
