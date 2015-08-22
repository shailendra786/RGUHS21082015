package com.dexpert.feecollection.main.payment.studentPayment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lookup_faculty")
public class FacultyBean {
	@GenericGenerator(name = "g1", strategy = "increment")
	@Id
	@GeneratedValue(generator = "g1")
	private Integer id;
	private String facultyKey, facultyValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFacultyKey() {
		return facultyKey;
	}

	public void setFacultyKey(String facultyKey) {
		this.facultyKey = facultyKey;
	}

	public String getFacultyValue() {
		return facultyValue;
	}

	public void setFacultyValue(String facultyValue) {
		this.facultyValue = facultyValue;
	}

}
