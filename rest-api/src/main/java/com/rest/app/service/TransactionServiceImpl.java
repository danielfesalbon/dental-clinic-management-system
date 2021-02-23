/**
 * 
 */
package com.rest.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.InventoryRepository;
import com.rest.app.repo.InvoiceRepository;
import com.rest.app.repo.InvoiceitemRepository;
import com.rest.app.repo.TransactionRepository;
import com.rest.app.table.Inventory;
import com.rest.app.table.Invoice;
import com.rest.app.table.Invoiceitem;
import com.rest.app.table.Transaction;
import com.rest.app.util.InvoiceRequest;
import com.rest.app.util.PaginationUtil;

/**
 * @author danielf
 *
 */
@Component
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private InvoiceitemRepository invoiceItemRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private PaginationUtil pageUtil;
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public List<Transaction> getTransactionList(Long invoiceid) {
		// TODO Auto-generated method stub
		try {

			Transaction tx = new Transaction();
			tx.setInvoiceid(invoiceid);
			Example<Transaction> example = Example.of(tx);
			return transactionRepository.findAll(example);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<Transaction>();
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("event", "New transaction");
			Invoice invoice = invoiceRepository.findById(transaction.getInvoiceid()).get();
			invoice.setPaid(invoice.getPaid().add(transaction.getAmount()));
			invoiceRepository.save(invoice);
			transaction.setTransactiondate(new Date());
			transactionRepository.save(transaction);
			response.put("event", "New transaction: " + transaction.getId());
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> getInvoiceList(Integer row, Integer page) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			response.put("pagevalues", pageUtil.getPageValues(row, (int) invoiceRepository.count()));
			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "id"));
			response.put("invoicelist", invoiceRepository.findAll(pagination).getContent());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveInvoice(InvoiceRequest invoicereq) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("event", "Saved new invoice record");
			invoicereq.getInvoice().setDatecreated(new Date());
			invoicereq.getInvoice().setPtname(invoicereq.getInvoice().getPtname().toUpperCase());
			invoiceRepository.save(invoicereq.getInvoice());
			Long invoiceid = invoicereq.getInvoice().getId();
			String createdby = invoicereq.getInvoice().getCreatedby();
			for (Invoiceitem i : invoicereq.getItems()) {
				i.setInvoiceid(invoiceid);
				i.setDatecreated(new Date());
				i.setCreatedby(createdby);
				if (i.getInventoryid() != null && inventoryRepository.existsById(i.getInventoryid())) {
					Inventory prod = inventoryRepository.findById(i.getInventoryid()).get();
					prod.setQuantity(prod.getQuantity() - i.getQuantity());
					inventoryRepository.save(prod);
					invoiceItemRepository.save(i);
				} else {
					invoiceItemRepository.save(i);
				}
			}
			response.put("flag", "success");
			response.put("event", "Saved new invoice record: " + invoiceid);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateInvoice(InvoiceRequest invoicereq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> getInvoice(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			if (invoiceRepository.existsById(id)) {
				response.put("invoice", invoiceRepository.findById(id).get());
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}
