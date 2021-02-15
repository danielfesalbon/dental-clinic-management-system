/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.table.Invoice;
import com.rest.app.table.Transaction;
import com.rest.app.table.Useraccount;
import com.rest.app.util.InvoiceRequest;

/**
 * @author danielf
 *
 */
@Component
public class TransactionServiceImpl implements TransactionService {

	@Override
	public List<Useraccount> getTransactionList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invoice> getInvoiceList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveInvoice(InvoiceRequest invoicereq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateInvoice(InvoiceRequest invoicereq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<InvoiceRequest> getInvoice(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
