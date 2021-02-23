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
@Table(name = "PATIENT")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "firstname")
	private String firstname;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "middlename")
	private String middlename;
	@Column(name = "email")
	private String email;
	@Column(name = "contact")
	private String contact;
	@Column(name = "address")
	private String address;
	@Column(name = "birthdate")
	private Date birthdate;
	@Column(name = "gender")
	private String gender;
	@Column(name = "datecreated")
	private Date datecreated;
	@Column(name = "createdby")
	private String createdby;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", middlename="
				+ middlename + ", email=" + email + ", contact=" + contact + ", address=" + address + ", birthdate="
				+ birthdate + ", gender=" + gender + ", datecreated=" + datecreated + ", createdby=" + createdby + "]";
	}

}
