/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Transaction;
import com.rest.app.util.InvoiceRequest;

/**
 * @author danielf
 *
 */
public interface TransactionService {

	List<Transaction> getTransactionList(Long invoiceid);

	ResponseEntity<Map<String, Object>> saveTransaction(Transaction transaction);

	ResponseEntity<Map<String, Object>> updateTransaction(Transaction transaction);

	ResponseEntity<Map<String, Object>> getInvoiceList(Integer row, Integer page);

	ResponseEntity<Map<String, Object>> saveInvoice(InvoiceRequest invoicereq);

	ResponseEntity<Map<String, Object>> updateInvoice(InvoiceRequest invoicereq);

	ResponseEntity<Map<String, Object>> getInvoice(Long id);

}
