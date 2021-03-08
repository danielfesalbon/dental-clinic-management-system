/**
 * 
 */
package com.rest.app.util;

import java.util.Date;

/**
 * @author danielf
 *
 */
public class AppointmentBody {

	private Long id;
	private Date scheddate;
	private String schedtime;
	private Long ptid;
	private String ptfirstname;
	private String ptlastname;
	private String ptcontact;
	private String ptaddress;
	private String remarks;
	private Date datecreated;
	private String doctor;
	private boolean done;
	private String service;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return "AppointmentBody [id=" + id + ", scheddate=" + scheddate + ", schedtime=" + schedtime + ", ptid=" + ptid
				+ ", ptfirstname=" + ptfirstname + ", ptlastname=" + ptlastname + ", ptcontact=" + ptcontact
				+ ", ptaddress=" + ptaddress + ", remarks=" + remarks + ", datecreated=" + datecreated + ", doctor="
				+ doctor + ", done=" + done + "]";
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getScheddate() {
		return scheddate;
	}

	public void setScheddate(Date scheddate) {
		this.scheddate = scheddate;
	}

	public String getSchedtime() {
		return schedtime;
	}

	public void setSchedtime(String schedtime) {
		this.schedtime = schedtime;
	}

	public Long getPtid() {
		return ptid;
	}

	public void setPtid(Long ptid) {
		this.ptid = ptid;
	}

	public String getPtfirstname() {
		return ptfirstname;
	}

	public void setPtfirstname(String ptfirstname) {
		this.ptfirstname = ptfirstname;
	}

	public String getPtlastname() {
		return ptlastname;
	}

	public void setPtlastname(String ptlastname) {
		this.ptlastname = ptlastname;
	}

	public String getPtcontact() {
		return ptcontact;
	}

	public void setPtcontact(String ptcontact) {
		this.ptcontact = ptcontact;
	}

	public String getPtaddress() {
		return ptaddress;
	}

	public void setPtaddress(String ptaddress) {
		this.ptaddress = ptaddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

}
