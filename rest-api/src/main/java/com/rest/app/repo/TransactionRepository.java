/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Transaction;

/**
 * @author danielf
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
