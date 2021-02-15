/**
 * 
 */
package com.rest.app.util;

import java.util.List;

import com.rest.app.table.Invoice;
import com.rest.app.table.Invoiceitem;

/**
 * @author danielf
 *
 */
public class InvoiceRequest {

	private Invoice invoice;
	private List<Invoiceitem> items;

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public List<Invoiceitem> getItems() {
		return items;
	}

	public void setItems(List<Invoiceitem> items) {
		this.items = items;
	}

}
