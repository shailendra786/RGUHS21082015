package com.dexpert.feecollection.main.payment.studentPayment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lookup_nationality")
public class NationalityBean {
	@GenericGenerator(name = "g1", strategy = "increment")
	@Id
	@GeneratedValue(generator = "g1")
	private Integer id;
	private String nationalityKey, nationalityValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNationalityKey() {
		return nationalityKey;
	}

	public void setNationalityKey(String nationalityKey) {
		this.nationalityKey = nationalityKey;
	}

	public String getNationalityValue() {
		return nationalityValue;
	}

	public void setNationalityValue(String nationalityValue) {
		this.nationalityValue = nationalityValue;
	}

}
