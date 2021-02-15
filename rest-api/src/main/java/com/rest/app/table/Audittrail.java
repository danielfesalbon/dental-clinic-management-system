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
@Table(name = "AUDITTRAIL")
public class Audittrail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "eventuser")
	private String eventuser;
	@Column(name = "eventdescription")
	private String eventdescription;
	@Column(name = "eventdate")
	private Date eventdate;
	@Column(name = "eventtime")
	private Time eventtime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEventuser() {
		return eventuser;
	}
	public void setEventuser(String eventuser) {
		this.eventuser = eventuser;
	}
	public String getEventdescription() {
		return eventdescription;
	}
	public void setEventdescription(String eventdescription) {
		this.eventdescription = eventdescription;
	}
	public Date getEventdate() {
		return eventdate;
	}
	public void setEventdate(Date eventdate) {
		this.eventdate = eventdate;
	}
	public Time getEventtime() {
		return eventtime;
	}
	public void setEventtime(Time eventtime) {
		this.eventtime = eventtime;
	}

	
}
