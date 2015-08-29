package com.dexpert.feecollection.main.users.affiliated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dexpert.feecollection.challan.TransactionBean;
import com.dexpert.feecollection.main.communication.email.EmailSessionBean;
import com.dexpert.feecollection.main.fee.PaymentDuesBean;
import com.dexpert.feecollection.main.fee.config.FcBean;
import com.dexpert.feecollection.main.fee.config.FcDAO;
import com.dexpert.feecollection.main.fee.config.FeeDetailsBean;
import com.dexpert.feecollection.main.fee.lookup.LookupBean;
import com.dexpert.feecollection.main.fee.lookup.LookupDAO;
import com.dexpert.feecollection.main.fee.lookup.values.FvBean;
import com.dexpert.feecollection.main.users.LoginBean;
import com.dexpert.feecollection.main.users.PasswordEncryption;
import com.dexpert.feecollection.main.users.RandomPasswordGenerator;
import com.dexpert.feecollection.main.users.parent.ParBean;
import com.dexpert.feecollection.main.users.parent.ParDAO;
import com.opensymphony.xwork2.ActionSupport;

public class AffAction extends ActionSupport {

	// Declare Global Variables Here
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession ses = request.getSession();
	List<AffBean> affBeansList = new ArrayList<AffBean>();
	Boolean saved = true;
	List<String> list = new ArrayList<String>();
	private Set<PaymentDuesBean> dueFeesSet;
	private List<AffBean> affBeans;
	private Integer parInstId;
	private LookupDAO lookupdao = new LookupDAO();
	public AffBean affInstBean;
	private ArrayList<AffFeeCalcDetail> calcList = new ArrayList<AffFeeCalcDetail>();
	private AffFeePropBean propbean;
	ArrayList<AffFeePropBean> dueList = new ArrayList<AffFeePropBean>();
	List<ParBean> parBeansList = new ArrayList<ParBean>();
	AffDAO affDao = new AffDAO();
	ParBean parBean;
	ParDAO parDAO = new ParDAO();
	FcDAO feeDAO = new FcDAO();
	static Logger log = Logger.getLogger(AffAction.class.getName());
	public List<AffBean> affInstList = new ArrayList<AffBean>();
	private ArrayList<Integer> paramIds = new ArrayList<Integer>();;
	ArrayList<AffBean> failureAffBeanList = new ArrayList<AffBean>();
	ArrayList<AffBean> existingInstitureRecordList = new ArrayList<AffBean>();
	private ArrayList<LookupBean> paramList2 = new ArrayList<LookupBean>();
	String fileFileName;
	FileInputStream inputStream;
	private File fileUpload;
	private ArrayList<FeeDetailsBean> feeList = new ArrayList<FeeDetailsBean>();
	private String fileUploadFileName;
	private Integer fileSize;
	private String contentType;
	private AffBean affBean;
	private List<TransactionBean> transactionDetailsForReport;

	// End of Global Variables

	// ---------------------------------------------------

	// Action Methods Here

	// registerInstitute()
	public String registerInstitute() throws Exception {
		log.info("Par Inst Name Is  ::" + parInstId);
		// log.info("paramset is "+affInstBean.getParamvalues().toString());
		List<String> instNameList = affDao.getCollegeNameList(affInstBean.getInstName());

		// HttpSession httpSession = request.getSession();
		// LoginBean loginBean = (LoginBean)
		// httpSession.getAttribute("loginUserBean");

		if (instNameList.isEmpty()) {

			if (parInstId == null) {
				parBeansList = parDAO.getUniversityList();
				request.setAttribute("msg", "Please Select University");
				return "failure";

			} else {
				String username;

				try {
					username = "Inst".concat(affInstBean.getInstName().replaceAll("\\s+", "").substring(0, 4)
							.concat(affDao.getRowCount().toString()));

				} catch (java.lang.NullPointerException e) {
					username = "Inst".concat(affInstBean.getInstName().replaceAll("\\s+", "").substring(0, 4)
							.concat("1"));

				}

				String password = RandomPasswordGenerator.generatePswd(6, 8, 1, 2, 0);

				PasswordEncryption.encrypt(password);
				String encryptedPwd = PasswordEncryption.encStr;

				LoginBean creds = new LoginBean();
				creds.setPassword(encryptedPwd);
				creds.setUserName(username);

				creds.setProfile(affInstBean.getLoginBean().getProfile());
				 ParBean parBean1 = new ParBean();
				parBean1 = parDAO.viewUniversity(parInstId);

				// one to many relationship
				parBean1.getAffBeanOneToManySet().add(affInstBean);

				affInstBean.setParBeanManyToOne(parBean1);
				parDAO.saveOrUpdate(parBean1, null);

				// for bidirectional relationship ,set parent record to child
				// record

				// login to institute
				creds.setAffBean(affInstBean);

				// one to one relationship
				// affInstBean.setParBeanOneToOne(parBean1);
				if (creds.getProfile().equals("Admin")) {

					// for bidirectional relationship ,set child record to
					// Parent
					// record
					affInstBean.setLoginBean(creds);

				}

				String path = request.getServletContext().getRealPath("/");
				path = path + File.separator;
				File f = new File(path + "/RGUHS/");
				f.mkdir();

				// add parent institute details to affInstBean

				affInstBean.setFileUpload(fileUpload);
				affInstBean.setFileUploadFileName(fileUploadFileName);

				log.info("The ID after saving is is " + affInstBean.getInstId());
				// if saved successfully generate credentials and forward
				// success

				affInstBean = affDao.saveOrUpdate(affInstBean, f + File.separator);

				// -----Code for sending email//--------------------
				EmailSessionBean email = new EmailSessionBean();
				email.sendEmail(affInstBean.getEmail(), "Welcome To FeeDesk!", username, password,
						affInstBean.getInstName());

				request.setAttribute("msg", "Institute Added Successfully");
				request.setAttribute("redirectLink", "Success.jsp");
				return SUCCESS;

			}
		}

		// else forward error message on input page

		else {
			log.info("College NAME ALREADY AVAILABLE");
			parBeansList = parDAO.getUniversityList();
			request.setAttribute("msg", "Institute Name is Already Registered");
			return "failure";
		}

	}

	// getInstituteDetails()
	public String getInstituteDetails() {
		String filterKey = null, filterVal = null;
		Date fromDate = new Date(), toDate = new Date();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		try {

			try {
				filterKey = request.getParameter("fKey").toString();
				filterVal = request.getParameter("fVal").toString();
			} catch (java.lang.NullPointerException e) {
				log.info("filter key is Null");
				log.info("filter value is Null");
			}

		} catch (Exception e) {
			e.printStackTrace();
			filterKey = "NONE";
		}
		affInstList = affDao.getInstitutes(filterKey, filterVal, idList, fromDate, toDate);
		return SUCCESS;
	}

	// get institute Details list

	public String getCollegeList() {

		affInstList = affDao.getCollegesList();

		return SUCCESS;
	}

	// get collgege list based on university ID
	public String getUniversityCollegeList() {
		try {
			HttpSession httpSession = request.getSession();
			LoginBean loginBean = (LoginBean) httpSession.getAttribute("loginUserBean");
			parBean = affDao.getUniversityCollegeList(loginBean);
			return SUCCESS;
		} catch (java.lang.NullPointerException e) {
			return ERROR;
		}

	}

	// get one college Detail to edit

	public String viewCollegeDetail() {

		String instituteId = request.getParameter("instId");
		Integer instId = Integer.parseInt(instituteId);
		affInstBean = affDao.viewInstDetail(instId);
		ses.setAttribute("sesAffBean", affInstBean);

		return SUCCESS;
	}

	// edit college Details

	public String editCollegeDetail() {

		String id = request.getParameter("instId");

		Integer instId = Integer.parseInt(id);

		affInstBean = affDao.getOneCollegeRecord(instId);

		return SUCCESS;

	}

	// update COllege Record

	public String updateCollegeDetail() {
		// log.info("paramlist is " + affInstBean.getParamvalues().toString());
		List<String> instNameList = affDao.getCollegeNameList(affInstBean.getInstName());
		log.info("list Size is ::" + instNameList.size());
		if (instNameList.isEmpty()) {

			affDao.updateCollege(affInstBean);

			request.setAttribute("msg", "Institute Updated Successfully");

			return SUCCESS;
		} else {

			log.info("College NaME ALREADY AVAILABLE");

			request.setAttribute("msg", "Institute Name is Already Registered");
			return "failure";

		}

	}

	public String configureCollegeParam() throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidAlgorithmParameterException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {

		log.info("parameter ids are " + paramIds.toString());
		HashMap<Integer, FvBean> valueMap = (HashMap<Integer, FvBean>) ses.getAttribute("sesParamMap");
		AffBean savedata = (AffBean) ses.getAttribute("sesAffBean");
		log.info("keys are " + valueMap.keySet().toString());
		for (int i = 0; i < paramIds.size(); i++) {
			savedata.getParamvalues().add(valueMap.get(paramIds.get(i)));
		}

		affDao.saveOrUpdate(savedata, null);
		ses.removeAttribute("sesAffBean");
		ses.removeAttribute("sesParamMap");
		request.setAttribute("msg", "Institute Updated Successfully");

		return SUCCESS;

	}

	// edit college doc

	public String editCollegeDoc() {
		String id = request.getParameter("instId");

		Integer instId = Integer.parseInt(id);

		affInstBean = affDao.getOneCollegeRecord(instId);
		return SUCCESS;

	}

	// update College Doc

	public String updateCollegeDoc() {

		String path = request.getServletContext().getRealPath("/");
		path = path + File.separator;
		File f = new File(path + "/RGUHS/");
		f.mkdir();

		affInstBean.setFileUpload(fileUpload);
		affInstBean.setFileUploadFileName(fileUploadFileName);
		affDao.updateCollegeDoc(affInstBean, f + File.separator);

		String msg = "File Successfully Updated";
		request.setAttribute("msg", msg);

		return SUCCESS;
	}

	// download File

	public String downloadFile() throws IOException {
		String docuId = request.getParameter("documentId");
		log.info("Document ID ::" + docuId);
		Integer id = Integer.parseInt(docuId);
		affInstBean = affDao.getOneCollegeRecord(id);
		FileOutputStream fileOuputStream = new FileOutputStream(affInstBean.getFileUploadFileName());
		fileOuputStream.write(affInstBean.getFilesByteSize());
		fileOuputStream.close();

		fileFileName = affInstBean.getFileUploadFileName();
		inputStream = new FileInputStream(affInstBean.getFileUploadFileName());

		return SUCCESS;
	}

	// add bulk colleges

	public String bulkCollegesAdd() throws Exception {
		log.info("file Name ::" + fileUploadFileName);
		log.info("file IS  ::" + fileUpload);

		// if loop to check format of file
		if (fileUploadFileName.endsWith(".xlsx")) {

			String path = request.getServletContext().getRealPath("/");
			path = path + File.separator;
			File f = new File(path + "/RGUHS/");
			f.mkdir();

			// try {
			affBeansList = affDao.importExcelFileToDatabase(fileUploadFileName, fileUpload, f + File.separator);
			// } catch (java.lang.NullPointerException e) {

			// request.setAttribute("msg",
			// "Please Create University Credential First");
			// return ERROR;
			// }
			Iterator<AffBean> iterator = affBeansList.iterator();
			while (iterator.hasNext()) {
				AffBean affBean = (AffBean) iterator.next();

			}

			return SUCCESS;

		}

		else {

			log.info("file Format not Match");
			String msg = "Please Select Proper File Format";
			request.setAttribute("msg", msg);
			return "failure";
		}

	}

	public String GetFees() {
		Integer instId = Integer.parseInt(request.getParameter("collId").trim());
		AffBean instbean = affDao.getOneCollegeRecord(instId);
		ArrayList<FeeDetailsBean> instfeeList = new ArrayList<FeeDetailsBean>(instbean.getFeeSet());
		feeList = feeDAO.GetFees("payee", "institute", null, null);
		for (int j = 0; j < instfeeList.size(); j++) {

			for (int i = 0; i < feeList.size(); i++) {
				if (instfeeList.get(j).getFeeId() == feeList.get(i).getFeeId()) {
					feeList.get(i).setGenericFlag(1);
				} else {
					feeList.get(i).setGenericFlag(0);
				}
			}

		}

		return SUCCESS;
	}

	public String AddFees() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException {
		ArrayList<FeeDetailsBean> feelist = new ArrayList<FeeDetailsBean>();

		AffBean collegedata = new AffBean();
		try {
			// Get College id from request
			Integer id = Integer.parseInt(request.getParameter("collId").trim());
			// Get Fee ids from request
			String feeidstr = request.getParameter("reqFeeIds").trim();
			log.info(feeidstr);
			List<String> FeeIds = Arrays.asList(feeidstr.split(","));
			ArrayList<Integer> FeeIdsInt = new ArrayList<Integer>();
			Iterator<String> idIt = FeeIds.iterator();
			log.info(FeeIds.toString());
			while (idIt.hasNext()) {
				try {
					FeeIdsInt.add(Integer.parseInt(idIt.next()));
				} catch (java.lang.NumberFormatException e) {
					// TODO Auto-generated catch block
					FeeIdsInt.add(-1);
				}
			}
			// Get College Data
			collegedata = affDao.getOneCollegeRecord(id);
			// Get Fees in Set
			feelist = feeDAO.GetFees("ids", null, null, FeeIdsInt);
			Set<FeeDetailsBean> feeset = collegedata.getFeeSet();
			Set<AffFeePropBean> propSet = collegedata.getFeeProps();
			for (int i = 0; i < feelist.size(); i++) {
				AffFeePropBean tempbean = new AffFeePropBean();
				tempbean.setFeeId(feelist.get(i).getFeeId());
				tempbean.setFeeName(feelist.get(i).getFeeName());
				// Set Due Bean
				PaymentDuesBean duebean = new PaymentDuesBean();
				duebean.setDateCalculated(new Date());
				duebean.setFeeId(tempbean.getFeeId());
				duebean.setPayments_to_date((double) 0);
				duebean.setPayee("Institute");
				duebean.setTotal_fee_amount((double) 0);
				duebean.setNetDue((double) 0);

				tempbean.setDueBean(duebean);
				tempbean.setCalcFlag(0);
				propSet.add(tempbean);

			}

			feeset.addAll(feelist);
			feelist.clear();
			// Add Fees to College Beans' FeeSet
			collegedata.setFeeSet(feeset);
			collegedata.setFeeProps(propSet);
			// ////

			// ///
			affDao.saveOrUpdate(collegedata, null);
			request.setAttribute("msg", "Fees Updated Successfully");
			// Save College Bean
			return SUCCESS;
		} catch (java.lang.NumberFormatException e) {
			e.printStackTrace();

			return ERROR;
		}
	}

	public String RemoveFee() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException {
		Integer feeId = Integer.parseInt(request.getParameter("reqFeeId").trim());
		Integer instId = Integer.parseInt(request.getParameter("collId").trim());
		AffBean collegedata = affDao.getOneCollegeRecord(instId);
		ArrayList<FeeDetailsBean> feeList = new ArrayList<FeeDetailsBean>(collegedata.getFeeSet());
		for (int i = 0; i < feeList.size(); i++) {
			if (feeId == feeList.get(i).getFeeId()) {
				feeList.remove(i);
			}
		}
		collegedata.setFeeSet(new HashSet<FeeDetailsBean>(feeList));
		collegedata = affDao.saveOrUpdate(collegedata, null);
		request.setAttribute("msg", "Fees Updated Successfully");
		request.setAttribute("redirectlink", "ViewCollegeFees?instId=" + instId);
		return SUCCESS;
	}

	public String GetParameterListInstitute() {
		try {

			paramList2 = lookupdao.getLookupData("Scope", "Institute", null, null);
			HashMap<Integer, FvBean> paramMap = new HashMap<Integer, FvBean>();
			Iterator<LookupBean> lIt = paramList2.iterator();
			while (lIt.hasNext()) {
				LookupBean temp = lIt.next();
				List<FvBean> valueList = new ArrayList<FvBean>();
				valueList = temp.getFvBeansList();
				Iterator<FvBean> pIt = valueList.iterator();
				while (pIt.hasNext()) {
					FvBean temp2 = pIt.next();
					paramMap.put(temp2.getFeeValueId(), temp2);
				}
			}

			ses.setAttribute("sesParamMap", paramMap);
			String instituteId = request.getParameter("instId");
			Integer instId = Integer.parseInt(instituteId);
			affInstBean = affDao.viewInstDetail(instId);
			ses.setAttribute("sesAffBean", affInstBean);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// Method to get Fee Properties' Details
	public String viewFeeProps() {
		// get institute id from request
		Integer reqinstId = Integer.parseInt(request.getParameter("instId").trim());
		// get fee id from request
		Integer reqfeeId = Integer.parseInt(request.getParameter("reqfeeId").trim());
		// get inst bean from database
		AffBean tempbean = affDao.getOneCollegeRecord(reqinstId);
		// populate feeprop bean with correct object from set
		Set<AffFeePropBean> feePropsSet = tempbean.getFeeProps();
		log.info("set size is " + feePropsSet.size());
		Iterator<AffFeePropBean> setIt = feePropsSet.iterator();
		while (setIt.hasNext()) {
			AffFeePropBean tempbean2 = new AffFeePropBean();
			tempbean2 = setIt.next();
			if (tempbean2.getFeeId() == reqfeeId) {
				propbean = tempbean2;
				return SUCCESS;
			}

		}
		return ERROR;
	}

	public String getDues() {
		// Get id from session
		Integer id = (Integer) ses.getAttribute("sesId");
		// get bean from db
		AffBean collbean = affDao.getOneCollegeRecord(id);
		// get feeprops set in list
		dueList = new ArrayList<AffFeePropBean>(collbean.getFeeProps());
		return SUCCESS;
	}

	// to get College Due Report

	public String collegeDueReport() {
		String profile = (String) ses.getAttribute("sesProfile");
		if (profile.contentEquals("Parent")) {
			LoginBean loginUser = (LoginBean) ses.getAttribute("loginUserBean");
			Integer universityId = loginUser.getParBean().getParInstId();
			log.info("university id is=" + universityId);
			List<Integer> institeIdes = parDAO.getIdesOfAllCollege(universityId);
			affBeans = affDao.getAllCollege(institeIdes);
			log.info("Aff Bean List" + affBeans.size());
			return "AllCollegeDues";
		} else if (profile.contentEquals("SU")) {

			log.info("Super Admin");
			affBeans = affDao.getAllTransactionRecordsForSU();
			return "AllCollegeDues";
		}
		// Get id from session
		Integer id = (Integer) ses.getAttribute("sesId");
		log.info("college id is=" + id);
		// get the affBean from db to get set of due
		affBean = affDao.getCollegeDues(id);
		dueFeesSet = affBean.getDueFeesSet();
		log.info("size of due fees set=" + dueFeesSet.size());
		return SUCCESS;
	}

	/*
	 * // to get College Due Report
	 * 
	 * public String collegeDueReport() { // Get id from session Integer id =
	 * (Integer) ses.getAttribute("sesId"); //get the affBean from db to get set
	 * of due affBean=affDao.getCollegeDues(id); return SUCCESS; }
	 */

	public String UpdateCalcParameters() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException {
		AffFeePropBean feePropbean = new AffFeePropBean();

		// Get Fee Id
		Integer feeId = (Integer) ses.getAttribute("sesFeeId");
		// Get Institute Id
		Integer InsId = (Integer) ses.getAttribute("sesInstId");
		// Get Institute Bean from dB
		AffBean instBean = affDao.getOneCollegeRecord(InsId);
		// Get Institute Fee Property Bean from Institute Bean
		Set<AffFeePropBean> feeProp = instBean.getFeeProps();
		Iterator<AffFeePropBean> feeIt = feeProp.iterator();
		while (feeIt.hasNext()) {
			AffFeePropBean temp = feeIt.next();
			if (temp.getFeeId() == feeId) {
				feePropbean = temp;
			}

		}
		// Add Fee Calc Detail Beans to Institute Fee Property Bean and update
		// the Institute Bean
		Set<AffFeeCalcDetail> multipliers = new HashSet<AffFeeCalcDetail>(calcList);
		feePropbean.setMultipliers(multipliers);
		feePropbean.setCalcFlag(1);
		instBean.getFeeProps().add(feePropbean);
		ArrayList<AffFeePropBean> calculatedFees = calculateFee(instBean);
		Set<AffFeePropBean> calculateSet = new HashSet<AffFeePropBean>(calculatedFees);
		instBean.setFeeProps(calculateSet);

		affDao.saveOrUpdate(instBean, null);

		// Remove session Attributes
		ses.removeAttribute("sesInstId");
		ses.removeAttribute("sesFeeId");
		return SUCCESS;
	}

	private ArrayList<AffFeePropBean> calculateFee(AffBean institute) {
		// Get Associated Fees
		ArrayList<AffFeePropBean> tempList = new ArrayList<AffFeePropBean>(institute.getFeeProps());
		ArrayList<AffFeePropBean> resList = new ArrayList<AffFeePropBean>(institute.getFeeProps());
		// Get First Fee and see if it is fixed or per applicant
		Iterator<AffFeePropBean> tempIt = tempList.iterator();
		while (tempIt.hasNext()) {

			AffFeePropBean propBean = tempIt.next();
			PaymentDuesBean due = new PaymentDuesBean();
			FeeDetailsBean feedetail = feeDAO.GetFees("id", null, propBean.getFeeId(), null).get(0);
			List<FcBean> configs = new ArrayList<FcBean>();
			configs = feedetail.getConfigs();
			HashMap<Integer, Double> configMap = new HashMap<Integer, Double>();
			Iterator<FcBean> configIt = configs.iterator();
			while (configIt.hasNext()) {
				FcBean tempconfig = configIt.next();
				configMap.put(tempconfig.getComboId(), tempconfig.getAmount());
			}
			// if Fixed update amount
			if (feedetail.getCal_mode() == 0) {
				log.info("Calculation Mode is fixed,... development pending on fixed mode");
			}

			// if per Applicant see if calc multipliers have been set
			if (feedetail.getCal_mode() == 1) {
				Set<AffFeeCalcDetail> mults = propBean.getMultipliers();
				Double feeDue = (double) 0;
				// if set then calculate
				if (mults.size() > 0) {
					Iterator<AffFeeCalcDetail> mulIt = mults.iterator();
					while (mulIt.hasNext()) {
						AffFeeCalcDetail mul = mulIt.next();
						Double amount = configMap.get(mul.getComboId());
						feeDue = feeDue + (amount * mul.getMultiplier());

					}
					PaymentDuesBean dueBean = propBean.getDueBean();
					dueBean.setTotal_fee_amount(feeDue);
					dueBean.setDateCalculated(new Date());
					dueBean.setNetDue(dueBean.getTotal_fee_amount() - dueBean.getPayments_to_date());
					propBean.setDueBean(dueBean);
					resList.add(propBean);
				}
				// if no then set zero

			}

		}
		return resList;

	}

	public String getInsTransactionDetails() {
		;
		String profile = (String) ses.getAttribute("sesProfile");
		Integer idOfLoginer = null;
		if (profile.contentEquals("Parent")) {
			LoginBean loginUser = (LoginBean) ses.getAttribute("loginUserBean");
			idOfLoginer = loginUser.getParBean().getParInstId();
			ParBean parBean = affDao.getTrasactionReportForUniversity(idOfLoginer);
			List<Integer> allCollegeId = parDAO.getIdesOfAllCollege(idOfLoginer);
			transactionDetailsForReport = affDao.getTransactionOfColleges(allCollegeId);
			log.info("Number of Transaction Done by Colleges Belongs to That University"
					+ transactionDetailsForReport.size());
			return SUCCESS;

		} else if (profile.contentEquals("SU")) {
			transactionDetailsForReport = affDao.getAllTransactionRecordsForSuper();
			log.info("List Size ::" + transactionDetailsForReport.size());
			return SUCCESS;

		} else {
			idOfLoginer = (Integer) ses.getAttribute("sesId");
			transactionDetailsForReport = affDao.getTransactionDetails(idOfLoginer, profile);
			log.info("inside else block%%%%%%%%%");
			return SUCCESS;
		}
	}

	// End of Action Methods
	// ---------------------------------------------------

	// Keep Getter Setter Methods Here
	public AffBean getAffInstBean() {
		return affInstBean;
	}

	public void setAffInstBean(AffBean affInstBean) {
		this.affInstBean = affInstBean;
	}

	public void setAffInstList(ArrayList<AffBean> affInstList) {
		this.affInstList = affInstList;
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public FileInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ArrayList<FeeDetailsBean> getFeeList() {
		return feeList;
	}

	public void setFeeList(ArrayList<FeeDetailsBean> feeList) {
		this.feeList = feeList;
	}

	public Boolean getSaved() {
		return saved;
	}

	public void setSaved(Boolean saved) {
		this.saved = saved;
	}

	public ArrayList<AffBean> getFailureAffBeanList() {
		return failureAffBeanList;
	}

	public void setFailureAffBeanList(ArrayList<AffBean> failureAffBeanList) {
		this.failureAffBeanList = failureAffBeanList;
	}

	public ArrayList<AffBean> getExistingInstitureRecordList() {
		return existingInstitureRecordList;
	}

	public void setExistingInstitureRecordList(ArrayList<AffBean> existingInstitureRecordList) {
		this.existingInstitureRecordList = existingInstitureRecordList;
	}

	public ArrayList<Integer> getParamIds() {
		return paramIds;
	}

	public void setParamIds(ArrayList<Integer> paramIds) {
		this.paramIds = paramIds;
	}

	public ArrayList<LookupBean> getParamList2() {
		return paramList2;
	}

	public void setParamList2(ArrayList<LookupBean> paramList2) {
		this.paramList2 = paramList2;
	}

	public AffFeePropBean getPropbean() {
		return propbean;
	}

	public void setPropbean(AffFeePropBean propbean) {
		this.propbean = propbean;
	}

	public ArrayList<AffFeePropBean> getDueList() {
		return dueList;
	}

	public void setDueList(ArrayList<AffFeePropBean> dueList) {
		this.dueList = dueList;
	}

	public List<AffBean> getAffBeansList() {
		return affBeansList;
	}

	public void setAffBeansList(List<AffBean> affBeansList) {
		this.affBeansList = affBeansList;
	}

	public void setAffBeansList(ArrayList<AffBean> affBeansList) {
		this.affBeansList = affBeansList;
	}

	public ArrayList<AffFeeCalcDetail> getCalcList() {
		return calcList;
	}

	public void setCalcList(ArrayList<AffFeeCalcDetail> calcList) {
		this.calcList = calcList;
	}

	public Integer getParInstId() {
		return parInstId;
	}

	public void setParInstId(Integer parInstId) {
		this.parInstId = parInstId;
	}

	public ParBean getParBean() {
		return parBean;
	}

	public void setParBean(ParBean parBean) {
		this.parBean = parBean;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<ParBean> getParBeansList() {
		return parBeansList;
	}

	public void setParBeansList(List<ParBean> parBeansList) {
		this.parBeansList = parBeansList;
	}

	public List<AffBean> getAffInstList() {
		return affInstList;
	}

	public void setAffInstList(List<AffBean> affInstList) {
		this.affInstList = affInstList;
	}

	public AffBean getAffBean() {
		return affBean;
	}

	public void setAffBean(AffBean affBean) {
		this.affBean = affBean;
	}

	public List<TransactionBean> getTransactionDetailsForReport() {
		return transactionDetailsForReport;
	}

	public void setTransactionDetailsForReport(List<TransactionBean> transactionDetailsForReport) {
		this.transactionDetailsForReport = transactionDetailsForReport;
	}

	public List<AffBean> getAffBeans() {
		return affBeans;
	}

	public void setAffBeans(List<AffBean> affBeans) {
		this.affBeans = affBeans;
	}

	public Set<PaymentDuesBean> getDueFeesSet() {
		return dueFeesSet;
	}

	public void setDueFeesSet(Set<PaymentDuesBean> dueFeesSet) {
		this.dueFeesSet = dueFeesSet;
	}

	// End of Getter Setter Methods
}
