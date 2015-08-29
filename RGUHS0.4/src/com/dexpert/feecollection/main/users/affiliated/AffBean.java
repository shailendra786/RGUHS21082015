package com.dexpert.feecollection.main.users.affiliated;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dexpert.feecollection.main.fee.PaymentDuesBean;
import com.dexpert.feecollection.main.fee.config.FeeDetailsBean;
import com.dexpert.feecollection.main.fee.lookup.values.FvBean;
import com.dexpert.feecollection.main.users.LoginBean;
import com.dexpert.feecollection.main.users.applicant.AppBean;
import com.dexpert.feecollection.main.users.parent.ParBean;

@Entity
@Table(name = "affiliated_institute_details")
public class AffBean implements Serializable {

	@GenericGenerator(name = "g1", strategy = "increment")
	@Id
	@GeneratedValue(generator = "g1")
	private Integer instId;

	@Column(unique = true)
	private String instName;

	private String contactPerson, place, email, contactNumber, mobileNum;
	private String instAddress;

	// --------------------------------------
	// to upload file
	private File fileUpload;

	private String fileUploadFileName;
	private Integer fileSize;

	@Lob
	@Column(name = "filesByteSize", columnDefinition = "mediumblob")
	byte[] filesByteSize;

	// ------------------------------------

	// one to one bidirectional relationship with login
	// its parent
	@OneToOne(cascade = CascadeType.ALL)
	LoginBean loginBean;

	// bidirectional many to one
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Parent_id_fk", referencedColumnName = "parInstId")
	ParBean parBeanManyToOne;

	// one to many relationship with Applicants (Students)
	@OneToMany(cascade = CascadeType.ALL, targetEntity = AppBean.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "InsId_Fk", referencedColumnName = "instId")
	Set<AppBean> aplBeanSet;

	// one to many relationship with FeeDetails)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "affiliatedinstitute_feedetails", joinColumns = @JoinColumn(name = "inst_id"), inverseJoinColumns = @JoinColumn(name = "feeId"))
	Set<FeeDetailsBean> feeSet;
	// one to one bidirectional relationship with student and college
	// child
/*
	@OneToOne(cascade = CascadeType.ALL)
	private ParBean parBeanOneToOne;*/

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "affBean")
	private AppBean appBean;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "affiliated_values", joinColumns = @JoinColumn(name = "inst_id"), inverseJoinColumns = @JoinColumn(name = "value_id"))
	Set<FvBean> paramvalues;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AffFeePropBean> feeProps;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="InsId_FK",referencedColumnName="instId")
	private Set<PaymentDuesBean> dueFeesSet;

	public Set<AppBean> getAplBeanSet() {
		return aplBeanSet;
	}

	public void setAplBeanSet(Set<AppBean> aplBeanSet) {
		this.aplBeanSet = aplBeanSet;
	}

	/*
	 * public Set<LoginBean> getLoginBeanSet() { return loginBeanSet; }
	 * 
	 * public void setLoginBeanSet(Set<LoginBean> loginBeanSet) {
	 * this.loginBeanSet = loginBeanSet; }
	 */

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getInstAddress() {
		return instAddress;
	}

	public void setInstAddress(String instAddress) {
		this.instAddress = instAddress;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getFilesByteSize() {
		return filesByteSize;
	}

	public void setFilesByteSize(byte[] filesByteSize) {
		this.filesByteSize = filesByteSize;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public AppBean getAppBean() {
		return appBean;
	}

	public void setAppBean(AppBean appBean) {
		this.appBean = appBean;
	}

	public Set<FeeDetailsBean> getFeeSet() {
		return feeSet;
	}

	public void setFeeSet(Set<FeeDetailsBean> feeSet) {
		this.feeSet = feeSet;
	}

	public Set<FvBean> getParamvalues() {
		return paramvalues;
	}

	public void setParamvalues(Set<FvBean> paramvalues) {
		this.paramvalues = paramvalues;
	}

	public Set<AffFeePropBean> getFeeProps() {
		return feeProps;
	}

	public void setFeeProps(Set<AffFeePropBean> feeProps) {
		this.feeProps = feeProps;
	}

	public Set<PaymentDuesBean> getDueFeesSet() {
		return dueFeesSet;
	}

	public void setDueFeesSet(Set<PaymentDuesBean> dueFeesSet) {
		this.dueFeesSet = dueFeesSet;
	}

	public ParBean getParBeanManyToOne() {
		return parBeanManyToOne;
	}

	public void setParBeanManyToOne(ParBean parBeanManyToOne) {
		this.parBeanManyToOne = parBeanManyToOne;
	}

	
}
