package com.dexpert.feecollection.main.payment.studentPayment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lookup_course")
public class CourseBean {

	@GenericGenerator(name = "g1", strategy = "increment")
	@Id
	@GeneratedValue(generator = "g1")
	private Integer id;

	private String courseKey, courseValue;

	public String getCourseKey() {
		return courseKey;
	}

	public void setCourseKey(String courseKey) {
		this.courseKey = courseKey;
	}

	public String getCourseValue() {
		return courseValue;
	}

	public void setCourseValue(String courseValue) {
		this.courseValue = courseValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
