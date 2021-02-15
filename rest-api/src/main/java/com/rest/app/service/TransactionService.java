/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Invoice;
import com.rest.app.table.Transaction;
import com.rest.app.table.Useraccount;
import com.rest.app.util.InvoiceRequest;

/**
 * @author danielf
 *
 */
public interface TransactionService {

	List<Useraccount> getTransactionList();

	ResponseEntity<Map<String, Object>> saveTransaction(Transaction transaction);

	ResponseEntity<Map<String, Object>> updateTransaction(Transaction transaction);

	List<Invoice> getInvoiceList();

	ResponseEntity<Map<String, Object>> saveInvoice(InvoiceRequest invoicereq);

	ResponseEntity<Map<String, Object>> updateInvoice(InvoiceRequest invoicereq);

	ResponseEntity<InvoiceRequest> getInvoice(Long id);

}
