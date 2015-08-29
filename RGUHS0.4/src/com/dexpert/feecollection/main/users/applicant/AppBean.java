package com.dexpert.feecollection.main.users.applicant;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dexpert.feecollection.main.payment.transaction.PayBean;
import com.dexpert.feecollection.main.users.LoginBean;
import com.dexpert.feecollection.main.users.affiliated.AffBean;

@Entity
@Table(name = "applicant_details")
public class AppBean implements Serializable {

	@GenericGenerator(name = "g1", strategy = "increment")
	@Id
	@GeneratedValue(generator = "g1")
	private Integer aplId;
	private String aplFirstName, aplLstName, aplEmail, aplAddress, aplMobilePri, aplMobileSec, enrollmentNumber,
			gender;

	// one to one bidirectional relationship with login
	@OneToOne(cascade = CascadeType.ALL)
	LoginBean loginBean;

	// one to many relationship of applicant with Payment
	@OneToMany(cascade = CascadeType.ALL, targetEntity = PayBean.class)
	@JoinColumn(name = "aplicantId_Fk", referencedColumnName = "aplId")
	private Set<PayBean> payBeansSet;

	// one to one bidirectional relationship with student and college

	// parent
	@ManyToOne(cascade = CascadeType.ALL,targetEntity=AffBean.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "College_id_fk", referencedColumnName = "instId")
	AffBean affBeanManyToOne;

	
	public AffBean getAffBeanManyToOne() {
		return affBeanManyToOne;
	}

	public void setAffBeanManyToOne(AffBean affBeanManyToOne) {
		this.affBeanManyToOne = affBeanManyToOne;
	}

	public Set<PayBean> getPayBeansSet() {
		return payBeansSet;
	}

	public void setPayBeansSet(Set<PayBean> payBeansSet) {
		this.payBeansSet = payBeansSet;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Integer getAplId() {
		return aplId;
	}

	public void setAplId(Integer aplId) {
		this.aplId = aplId;
	}

	public String getAplLstName() {
		return aplLstName;
	}

	public void setAplLstName(String aplLstName) {
		this.aplLstName = aplLstName;
	}

	public String getAplAddress() {
		return aplAddress;
	}

	public void setAplAddress(String aplAddress) {
		this.aplAddress = aplAddress;
	}

	public String getAplFirstName() {
		return aplFirstName;
	}

	public void setAplFirstName(String aplFirstName) {
		this.aplFirstName = aplFirstName;
	}

	public String getAplEmail() {
		return aplEmail;
	}

	public void setAplEmail(String aplEmail) {
		this.aplEmail = aplEmail;
	}

	public String getAplMobilePri() {
		return aplMobilePri;
	}

	public void setAplMobilePri(String aplMobilePri) {
		this.aplMobilePri = aplMobilePri;
	}

	public String getAplMobileSec() {
		return aplMobileSec;
	}

	public void setAplMobileSec(String aplMobileSec) {
		this.aplMobileSec = aplMobileSec;
	}

	public String getEnrollmentNumber() {
		return enrollmentNumber;
	}

	public void setEnrollmentNumber(String enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
