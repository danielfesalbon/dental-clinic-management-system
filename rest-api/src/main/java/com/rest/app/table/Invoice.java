/**
 * 
 */
package com.rest.app.table;

import java.math.BigDecimal;
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
@Table(name = "INVOICE")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "ptid")
	private Long ptid;
	@Column(name = "ptname")
	private String ptname;
	@Column(name = "ptcontact")
	private String ptcontact;
	@Column(name = "ptaddress")
	private String ptaddress;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "paid")
	private BigDecimal paid;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "datecreated")
	private Date datecreated;
	@Column(name = "updatedby")
	private String updatedby;
	@Column(name = "dateupdated")
	private Date dateupdated;

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

	public String getPtname() {
		return ptname;
	}

	public void setPtname(String ptname) {
		this.ptname = ptname;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Date getDateupdated() {
		return dateupdated;
	}

	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}

}
