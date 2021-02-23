/**
 * 
 */
package com.rest.app.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Inventory;

/**
 * @author danielf
 *
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	List<Inventory> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
