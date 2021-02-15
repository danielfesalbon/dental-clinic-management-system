/**
 * 
 */
package com.rest.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.TransactionService;
import com.rest.app.table.Invoice;
import com.rest.app.table.Transaction;
import com.rest.app.table.Useraccount;
import com.rest.app.util.InvoiceRequest;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/transaction")
@Component
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/list")
	public List<Useraccount> getTransactionList() {
		return transactionService.getTransactionList();
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveTransaction(@RequestBody Transaction transaction) {
		return transactionService.saveTransaction(transaction);
	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateTransaction(@RequestBody Transaction transaction) {
		return transactionService.updateTransaction(transaction);
	}

	@GetMapping("/invoice/list")
	public List<Invoice> getInvoiceList() {
		return transactionService.getInvoiceList();
	}

	@GetMapping("/invoice/get")
	public ResponseEntity<InvoiceRequest> getInvoice(@RequestParam(required = false, name = "id") Long id) {
		return transactionService.getInvoice(id);
	}

	@PostMapping("/invoice/save")
	public ResponseEntity<Map<String, Object>> saveInvoice(@RequestBody InvoiceRequest invoicereq) {
		return transactionService.saveInvoice(invoicereq);
	}

	@PutMapping("/invoice/update")
	public ResponseEntity<Map<String, Object>> updateInvoice(@RequestBody InvoiceRequest invoicereq) {
		return transactionService.updateInvoice(invoicereq);
	}

}
