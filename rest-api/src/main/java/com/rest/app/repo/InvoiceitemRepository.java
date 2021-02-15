/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Invoiceitem;

/**
 * @author danielf
 *
 */
public interface InvoiceitemRepository extends JpaRepository<Invoiceitem, Long> {

}
