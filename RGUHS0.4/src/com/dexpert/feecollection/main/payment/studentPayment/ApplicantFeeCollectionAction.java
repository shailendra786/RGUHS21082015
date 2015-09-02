package com.dexpert.feecollection.main.payment.studentPayment;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dexpert.feecollection.challan.TransactionBean;
import com.dexpert.feecollection.main.fee.config.FcDAO;
import com.dexpert.feecollection.main.fee.lookup.LookupAction;
import com.dexpert.feecollection.main.users.affiliated.AffBean;
import com.dexpert.feecollection.main.users.affiliated.AffDAO;
import com.dexpert.feecollection.main.users.applicant.AppBean;
import com.dexpert.feecollection.main.users.applicant.AppDAO;
import com.opensymphony.xwork2.ActionSupport;

public class ApplicantFeeCollectionAction extends ActionSupport {

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
    ServletContext context=request.getServletContext();
    
    public String SabPaisaURL = "49.50.72.228:8080";
	public String returnUrl = "http://49.50.72.228:8080/RGUHS0.4/ReturnPage.jsp";
	String clientFailureUrl = "http://49.50.72.228:8080/RGUHS0.4/Login.jsp";

	HttpSession httpSession = request.getSession();
	static Logger log = Logger.getLogger(ApplicantFeeCollectionAction.class.getName());

	Map<String, String> serviceList = new HashMap<String, String>();
	Map<String, String> serviceListSelected = new HashMap<String, String>();
	Map<String, String> courseList = new HashMap<String, String>();
	Map<String, String> courseListSelected = new HashMap<String, String>();
	Map<String, String> nationalityList = new HashMap<String, String>();
	Map<String, String> nationalitySelected = new HashMap<String, String>();
	Map<String, String> facultyList = new HashMap<String, String>();
	Map<String, String> facultyListSelected = new HashMap<String, String>();
	private String noValidate = "1";
	ApplicantFeeCollectionBean feeCollectionBean = new ApplicantFeeCollectionBean();
	ApplicantFeeCollectionDAO dao = new ApplicantFeeCollectionDAO();
	AppBean appBean1;
	List<ApplicantFeeCollectionBean> collectionBeanList = new ArrayList<ApplicantFeeCollectionBean>();
	ApplicantFeeCollectionDAO afc = new ApplicantFeeCollectionDAO();
	AppDAO appDAO = new AppDAO();
	FcDAO fcDAO = new FcDAO();
	AffDAO affDAO = new AffDAO();

	// //

	public String responseHandelling() {

		String respCode = request.getParameter("RPS");
		log.info("responese code:::::: "+respCode);
		String txnId = request.getParameter("txnID");
		log.info("Transaction id returned by sabpaisa="+txnId);
		String paymentMode = request.getParameter("paymentMode");
		log.info("payment mode is::::"+paymentMode);
		if (paymentMode.contentEquals("Cash") || paymentMode.contentEquals("Cheque")||paymentMode.contentEquals("null")) {
			if (respCode.contentEquals("0")) {
				afc.updateTransactionStatus(txnId, null, paymentMode);
			} else  {
				afc.updateTransactionStatus(txnId, "Cancelled", paymentMode);
				return "home";
			}

		} else {
			if (respCode.equals("0")) {
				httpSession = (HttpSession) request.getServletContext().getAttribute(txnId);
				HashMap<String, String> hmap = (HashMap<String, String>) httpSession.getAttribute("hmap");
				String dueString=hmap.get("dueString");
				String enrollmentNumber=hmap.get("enrollId");
				Integer insId=null;
				if(enrollmentNumber==null){
				try{	
				insId=Integer.parseInt(hmap.get("insId"));
				}catch(NumberFormatException nf)
				{
				insId=null;	
				}
				}
			 afc.updateTransactionStatus(txnId, "Paid", paymentMode);	
			 afc.updateFeeduesTableDetail(dueString, enrollmentNumber, insId);

			} else {

			}
		}
		return SUCCESS;
	}

	// get Student Service Detail

	public String studentServiceDetail() {

		serviceList = dao.serviceTypeList();
		courseList = dao.courseList();
		facultyList = dao.facultyList();
		nationalityList = dao.nationalityList();

		return SUCCESS;
	}

	public String submitParameter() {

		ApplicantFeeCollectionBean tempBean = feeCollectionBean;

		try {

			feeCollectionBean = afc.calculateTotalFee(feeCollectionBean);

		} catch (java.util.NoSuchElementException e) {

			feeCollectionBean.setFee(0.0);
			log.info("Combination Not available");
		}

		feeCollectionBean.setService_type(tempBean.getService_type());
		feeCollectionBean.setCourse(tempBean.getCourse());
		feeCollectionBean.setNationality(tempBean.getNationality());
		feeCollectionBean.setFaculty(tempBean.getFaculty());

		// appBean1 = appDAO.getUserDetail(appBean1.getEnrollmentNumber());

		serviceListSelected.put(feeCollectionBean.getService_type(),
				serviceList.get(feeCollectionBean.getService_type()));

		facultyListSelected.put(feeCollectionBean.getFaculty(), facultyList.get(feeCollectionBean.getFaculty()));

		courseListSelected.put(feeCollectionBean.getCourse(), courseList.get(feeCollectionBean.getCourse()));

		nationalitySelected.put(feeCollectionBean.getNationality(),
				nationalityList.get(feeCollectionBean.getNationality()));

		serviceList = dao.serviceTypeList();
		courseList = dao.courseList();
		facultyList = dao.facultyList();
		nationalityList = dao.nationalityList();

		return SUCCESS;
	}

	// jumping to payment Gateway for RGUHS

	public void studentToPaymentGateway() throws IOException {
		String fee = request.getParameter("feeValue");
		String url = null;
		TransactionBean tran = new TransactionBean();
		// HttpSession httpSession =
		// ServletActionContext.getRequest().getSession();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(timestamp.getTime());
		String txnId = Idgenerator.transxId();
		log.info("Session Started");
		String enrolId = request.getParameter("enrollId");
		appBean1 = appDAO.getUserDetail(enrolId);
		if (appBean1 != null) {

			// tran.setDueString(dueString);
			tran.setInsId(appBean1.getAffBeanManyToOne().getInstId());
			tran.setPayeeAdd(appBean1.getAplAddress());
			tran.setPayeeAmount(Double.parseDouble(request.getParameter("feeValue")));
			tran.setPayeeEmail(appBean1.getAplEmail());
			tran.setPayeeMob(appBean1.getAplMobilePri());
			tran.setPayeeName(appBean1.getAplFirstName() + appBean1.getAplLstName());
			tran.setStatus("Pending");
			tran.setTransDate(date);
			tran.setTxnId(txnId);
			String user = appBean1.getAplFirstName().concat(" ").concat(appBean1.getAplLstName());
			url = "http://"+SabPaisaURL+"/SabPaisa?Name=" + user + "&amt=" + fee + "&RollNo="
					+ appBean1.getEnrollmentNumber() + "&Contact=" + appBean1.getAplMobilePri() + "&Email="
					+ appBean1.getAplEmail() + "&client=RGUHS" + "&ru=" + returnUrl + "&hmap=" + "&txnId=" + txnId+"&failureURL="+clientFailureUrl;

		} else {
			// tran.setDueString(dueString);
			// tran.setInsId(insId);
			// tran.setPayeeAdd(affBean.getInstAddress());
			tran.setPayeeAmount(Double.parseDouble(fee));
			tran.setPayeeEmail(request.getParameter("email"));
			tran.setPayeeMob(request.getParameter("contact"));
			tran.setPayeeName(request.getParameter("firstName").concat(request.getParameter("lstName")));
			tran.setStatus("Pending");
			tran.setTransDate(date);
			tran.setTxnId(txnId);
			String user = request.getParameter("firstName").concat(request.getParameter("lstName"));
			url = "http://"+SabPaisaURL+"/SabPaisa?Name=" + user + "&amt=" + fee + "&RollNo=" + enrolId + "&Contact="
					+ request.getParameter("contact") + "&Email=" + request.getParameter("email") + "&client=RGUHS"
					+ "&ru=" + returnUrl + "&hmap=" + "&txnId=" + txnId+"&failureURL="+clientFailureUrl;

		}

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("enrollId", enrolId);
		hashMap.put("txnID", txnId);
		hashMap.put("QP", "True");
		httpSession.setAttribute("dueStr", "");
		httpSession.setAttribute("hmap", hashMap);
		httpSession.getServletContext().setAttribute(txnId, httpSession);

		// String returnUrl =
		// "http://49.50.72.228:8080/RGUHS0.3.2/ReturnPage.jsp";
		appBean1 = appDAO.getUserDetail(enrolId);

		dao.saveStudentTransaction(tran);

		// String url = "http://49.50.72.228:8080/SabPaisa?Name=" + user +
		// "&amt=" + fee + "&RollNo="
		// + appBean1.getEnrollmentNumber() + "&Contact=" +
		// appBean1.getAplMobilePri() + "&Email="
		// + appBean1.getAplEmail() + "&client=RGUHS" + "&ru=" + returnUrl +
		// "&hmap=" + "&txnId=" + transId;
		response.sendRedirect(url);

	}

	// jumping to payment Gateway

	public void instPaymentGateway() throws IOException {

		String fee = request.getParameter("amt");
		String user = request.getParameter("feeName");
		log.info("Fee Name is" + user);
		Integer feeId = fcDAO.getFeeIdByName(user);
		String dueString = feeId.toString().concat("~").concat(fee);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(timestamp.getTime());
		Integer insId = (Integer) httpSession.getAttribute("sesId");
		AffBean affBean = affDAO.getOneCollegeRecord(insId);
		String txnId = Idgenerator.transxId();
		log.info("Transaction id generated by client"+txnId);
		TransactionBean tran = new TransactionBean();
		tran.setDueString(dueString);
		tran.setInsId(insId);
		tran.setPayeeAdd(affBean.getInstAddress());
		tran.setPayeeAmount(Double.parseDouble(fee));
		tran.setPayeeEmail(affBean.getEmail());
		tran.setPayeeMob(affBean.getContactNumber());
		tran.setPayeeName(affBean.getInstName());
		tran.setStatus("Pending");
		tran.setTransDate(date);
		tran.setTxnId(txnId);
		//
		dao.insertPaymentDetails(tran);

		

		HashMap<String, String> hashMap = new HashMap<String, String>();

		hashMap.put("insId", insId.toString());
		hashMap.put("txnID", txnId);
		hashMap.put("dueString",dueString);
		httpSession.setAttribute("hmap", hashMap);
		httpSession.getServletContext().setAttribute(txnId, httpSession);

		

		String url = "http://"+SabPaisaURL+"/SabPaisa?Name=" + affBean.getInstName() + "&amt=" + fee + "&txnId=" + txnId
				+ "&RollNo=" + null + "&client=RGUHS" + "&ru=" + returnUrl + "&Contact=" + affBean.getContactNumber()+"&failureURL="+clientFailureUrl;

		response.sendRedirect(url);


	}

	// /
	public ApplicantFeeCollectionBean getFeeCollectionBean() {
		return feeCollectionBean;
	}

	public void setFeeCollectionBean(ApplicantFeeCollectionBean feeCollectionBean) {
		this.feeCollectionBean = feeCollectionBean;
	}

	public List<ApplicantFeeCollectionBean> getCollectionBeanList() {
		return collectionBeanList;
	}

	public void setCollectionBeanList(List<ApplicantFeeCollectionBean> collectionBeanList) {
		this.collectionBeanList = collectionBeanList;
	}

	public AppBean getAppBean1() {
		return appBean1;
	}

	public void setAppBean1(AppBean appBean1) {
		this.appBean1 = appBean1;
	}

	public Map<String, String> getServiceList() {
		return serviceList;
	}

	public void setServiceList(Map<String, String> serviceList) {
		this.serviceList = serviceList;
	}

	public Map<String, String> getCourseList() {
		return courseList;
	}

	public void setCourseList(Map<String, String> courseList) {
		this.courseList = courseList;
	}

	public Map<String, String> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(Map<String, String> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public Map<String, String> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(Map<String, String> facultyList) {
		this.facultyList = facultyList;
	}

	public Map<String, String> getServiceListSelected() {
		return serviceListSelected;
	}

	public void setServiceListSelected(Map<String, String> serviceListSelected) {
		this.serviceListSelected = serviceListSelected;
	}

	public String getNoValidate() {
		return noValidate;
	}

	public void setNoValidate(String noValidate) {
		this.noValidate = noValidate;
	}

}
