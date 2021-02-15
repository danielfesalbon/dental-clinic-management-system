/**
 * 
 */
package com.rest.app.table;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author danielf
 *
 */
@Entity
@Table(name = "APPOINTMENT")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "scheddate")
	private Date scheddate;
	@Column(name = "schedtime")
	private Time schedtime;
	@Column(name = "ptid")
	private Long ptid;
	@Column(name = "ptfirstname")
	private String ptfirstname;
	@Column(name = "ptlastname")
	private String ptlastname;
	@Column(name = "ptcontact")
	private String ptcontact;
	@Column(name = "ptaddress")
	private String ptaddress;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "datecreated")
	private Date datecreated;
	@Column(name = "doctor")
	private String doctor;

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

	public Time getSchedtime() {
		return schedtime;
	}

	public void setSchedtime(Time schedtime) {
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
