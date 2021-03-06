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
import com.rest.app.table.Transaction;
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
	public List<Transaction> getTransactionList(@RequestParam(required = false, name = "invoiceid") Long invoiceid) {
		return transactionService.getTransactionList(invoiceid);
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
	public ResponseEntity<Map<String, Object>> getInvoiceList(@RequestParam(required = false, name = "row") Integer row,
			@RequestParam(required = false, name = "page") Integer page) {
		return transactionService.getInvoiceList(row, page);
	}

	@GetMapping("/invoice/get")
	public ResponseEntity<Map<String, Object>> getInvoice(@RequestParam(required = false, name = "id") Long id) {
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
