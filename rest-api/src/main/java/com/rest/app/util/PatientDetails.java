/**
 * 
 */
package com.rest.app.util;

import java.util.List;

import com.rest.app.table.Appointment;
import com.rest.app.table.History;
import com.rest.app.table.Invoice;
import com.rest.app.table.Patient;
import com.rest.app.table.Prescription;
import com.rest.app.table.Transaction;

/**
 * @author danielf
 *
 */
public class PatientDetails {

	private Patient patient;
	private List<History> history;
	private PaginateValues historypage;
	private List<Invoice> invoices;
	private PaginateValues invoicepage;
	private List<Transaction> tranasctions;
	private PaginateValues tranasctionpage;
	private List<Appointment> appointments;
	private PaginateValues appointmentpage;
	private List<Prescription> prescriptions;
	private PaginateValues prescriptiopage;

	public PaginateValues getHistorypage() {
		return historypage;
	}

	public void setHistorypage(PaginateValues historypage) {
		this.historypage = historypage;
	}

	public PaginateValues getInvoicepage() {
		return invoicepage;
	}

	public void setInvoicepage(PaginateValues invoicepage) {
		this.invoicepage = invoicepage;
	}

	public PaginateValues getTranasctionpage() {
		return tranasctionpage;
	}

	public void setTranasctionpage(PaginateValues tranasctionpage) {
		this.tranasctionpage = tranasctionpage;
	}

	public PaginateValues getAppointmentpage() {
		return appointmentpage;
	}

	public void setAppointmentpage(PaginateValues appointmentpage) {
		this.appointmentpage = appointmentpage;
	}

	public PaginateValues getPrescriptiopage() {
		return prescriptiopage;
	}

	public void setPrescriptiopage(PaginateValues prescriptiopage) {
		this.prescriptiopage = prescriptiopage;
	}

	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<History> getHistory() {
		return history;
	}

	public void setHistory(List<History> history) {
		this.history = history;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<Transaction> getTranasctions() {
		return tranasctions;
	}

	public void setTranasctions(List<Transaction> tranasctions) {
		this.tranasctions = tranasctions;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

}
