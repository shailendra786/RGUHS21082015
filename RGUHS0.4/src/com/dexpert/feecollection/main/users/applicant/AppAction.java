package com.dexpert.feecollection.main.users.applicant;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dexpert.feecollection.main.users.LoginBean;
import com.dexpert.feecollection.main.users.affiliated.AffAction;
import com.dexpert.feecollection.main.users.affiliated.AffBean;
import com.dexpert.feecollection.main.users.affiliated.AffDAO;
import com.opensymphony.xwork2.ActionSupport;

public class AppAction extends ActionSupport {

	// Declare Global Variables Here
	AppBean appBean1, appBean;
	List<AppBean> appBeansList = new ArrayList<AppBean>();
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	static Logger log = Logger.getLogger(AffAction.class.getName());
	AffBean affBean = new AffBean();
	Integer aplInstId;
	List<AffBean> affInstList = new ArrayList<AffBean>();
	String fileFileName;
	FileInputStream inputStream;
	private File fileUpload;
	private String fileUploadFileName;
	AffDAO affDAO = new AffDAO();

	private String contentType;
	public AppDAO aplDAO = new AppDAO();

	// End of Global Variables

	// ---------------------------------------------------

	// Action Methods Here

	public String registerStudent() {

		log.info("Applicant Name  ::" + appBean1.getAplFirstName());

		List<String> existEnrollmentList = aplDAO.existingEnrollNum(appBean1);
		if (existEnrollmentList.isEmpty()) {
			if (aplInstId == null) {
				request.setAttribute("msg", "Please Select College Name");
				affInstList = affDAO.getCollegesList();
				return "failure";
			}

			appBean1 = aplDAO.saveOrUpdate(appBean1, aplInstId);
			request.setAttribute("msg", "Student Addedd Successfully");
			return SUCCESS;

		} else {
			log.info("4");
			request.setAttribute("msg", "Enrollment Number Already Registered");
			affInstList = affDAO.getCollegesList();
			return "failure";
		}

	}

	// to get list of All Students

	public String getStudentList() {
		HttpSession httpSession = request.getSession();
		LoginBean loginBean = (LoginBean) httpSession.getAttribute("loginUserBean");

		 //log.info("Login User Name ::" + loginBean.getUserName());
		// log.info("Login inst ID ::" + loginBean.getAffBean().getInstId());
		// log.info("Login inst Name ::" +
		// loginBean.getAffBean().getInstName());

		// appBeansList = aplDAO.getAllStudentList();
		affBean = aplDAO.getStudentDetail(loginBean);

		return SUCCESS;

	}

	// to view Applicant Detail
	public String viewApplicant() {

		String apId = request.getParameter("applicantId");
		Integer appId = Integer.parseInt(apId);

		appBean1 = aplDAO.viewApplicantDetail(appId);
		// log.info("College Name ::" + appBean1.getAffBean().getInstName());
		return SUCCESS;
	}

	public String authenticateStudent() {
		log.info("User Entered Enrollment Number is ::" + appBean1.getEnrollmentNumber());

		appBeansList = aplDAO.getStudentDetailByEnrollMentNumber(appBean1.getEnrollmentNumber());

		if (appBeansList.isEmpty()) {
			log.info("Invalid Enrollment ID");
			request.setAttribute("msg", "Please Enter valid Enrollment Number");
			return "failure";

		}

		return SUCCESS;

	}

	// add Bulk Students
	public String addBulkStudents() throws Exception {

		if (fileUploadFileName.endsWith(".xlsx")) {

			String path = request.getServletContext().getRealPath("/");
			path = path + File.separator;
			File f = new File(path + "/RGUHS/");
			f.mkdir();

			appBeansList = aplDAO.importExcelFileToDatabase(fileUploadFileName, fileUpload, f + File.separator);

			return SUCCESS;

		}

		else {

			log.info("file Format not Match");
			String msg = "Please Select Proper File Format";
			request.setAttribute("msg", msg);
			return "failure";
		}

	}

	// End of Action Methods

	// ---------------------------------------------------

	// Keep Getter Setter Methods Here
	public AppBean getAppBean1() {
		return appBean1;
	}

	public void setAppBean1(AppBean appBean1) {
		this.appBean1 = appBean1;
	}

	public List<AppBean> getAppBeansList() {
		return appBeansList;
	}

	public void setAppBeansList(List<AppBean> appBeansList) {
		this.appBeansList = appBeansList;
	}

	public AppBean getAppBean() {
		return appBean;
	}

	public void setAppBean(AppBean appBean) {
		this.appBean = appBean;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public FileInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public AffBean getAffBean() {
		return affBean;
	}

	public void setAffBean(AffBean affBean) {
		this.affBean = affBean;
	}

	public Integer getAplInstId() {
		return aplInstId;
	}

	public void setAplInstId(Integer aplInstId) {
		this.aplInstId = aplInstId;
	}

	public List<AffBean> getAffInstList() {
		return affInstList;
	}

	public void setAffInstList(List<AffBean> affInstList) {
		this.affInstList = affInstList;
	}

}
