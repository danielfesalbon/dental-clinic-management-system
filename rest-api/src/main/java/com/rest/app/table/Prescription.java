/**
 * 
 */
package com.rest.app.table;

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
@Table(name = "PRESCRIPTION")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "ptid")
	private Long ptid;
	@Column(name = "presremarks")
	private String presremarks;
	@Column(name = "prescribedby")
	private String prescribedby;
	@Column(name = "dateprescribed")
	private Date dateprescribed;
	@Column(name = "active")
	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPtid() {
		return ptid;
	}

	public void setPtid(Long ptid) {
		this.ptid = ptid;
	}

	public String getPresremarks() {
		return presremarks;
	}

	public void setPresremarks(String presremarks) {
		this.presremarks = presremarks;
	}

	public String getPrescribedby() {
		return prescribedby;
	}

	public void setPrescribedby(String prescribedby) {
		this.prescribedby = prescribedby;
	}

	public Date getDateprescribed() {
		return dateprescribed;
	}

	public void setDateprescribed(Date dateprescribed) {
		this.dateprescribed = dateprescribed;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
