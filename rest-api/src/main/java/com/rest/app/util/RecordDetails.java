/**
 * 
 */
package com.rest.app.util;

import java.util.List;

import com.rest.app.table.Patient;

/**
 * @author danielf
 *
 */
public class RecordDetails {

	private List<Patient> patients;
	private PaginateValues pagevalues;

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public PaginateValues getPagevalues() {
		return pagevalues;
	}

	public void setPagevalues(PaginateValues pagevalues) {
		this.pagevalues = pagevalues;
	}

}
