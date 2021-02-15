/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Inventory;

/**
 * @author danielf
 *
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
