/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.History;

/**
 * @author danielf
 *
 */
public interface HistoryRepository extends JpaRepository<History, Long> {

}
