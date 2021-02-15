/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Invoice;

/**
 * @author danielf
 *
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
