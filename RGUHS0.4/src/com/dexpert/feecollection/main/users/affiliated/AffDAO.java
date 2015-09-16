package com.dexpert.feecollection.main.users.affiliated;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.dexpert.feecollection.challan.TransactionBean;
import com.dexpert.feecollection.main.ConnectionClass;
import com.dexpert.feecollection.main.communication.email.EmailSessionBean;
import com.dexpert.feecollection.main.fee.PaymentDuesBean;
import com.dexpert.feecollection.main.users.LoginBean;
import com.dexpert.feecollection.main.users.PasswordEncryption;
import com.dexpert.feecollection.main.users.RandomPasswordGenerator;
import com.dexpert.feecollection.main.users.parent.ParBean;
import com.dexpert.feecollection.main.users.parent.ParDAO;

public class AffDAO {

	// Declare Global Variables Here
	public static SessionFactory factory = ConnectionClass.getFactory();
	static Logger log = Logger.getLogger(AffDAO.class.getName());
	static Boolean isExist = false;
	ParDAO parDAO = new ParDAO();
	boolean isInserted = true;

	// static ArrayList<AffBean> existingCollegeList = new ArrayList<AffBean>();

	// End of Global Variables

	// ---------------------------------------------------

	// DAO Methods Here

	public List<TransactionBean> getTransactionOfColleges(List<Integer> ides) {
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(TransactionBean.class);
		List<TransactionBean> collegesTransactionList = criteria.add(Restrictions.in("insId", ides)).list();
		session.close();
		return collegesTransactionList;
	}

	public ParBean getTrasactionReportForUniversity(Integer universityId) {
		Session session = factory.openSession();
		ParBean parBean = (ParBean) session.get(ParBean.class, universityId);
		session.close();
		return parBean;
	}

	// saveOrUpdate()
	@SuppressWarnings("resource")
	public AffBean saveOrUpdate(AffBean affInstBean, String path) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidAlgorithmParameterException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {

		// Declarations
		// Open session from session factory
		Session session = factory.openSession();

		try {
			byte[] bFile = null;
			Integer fileSize = null;
			// file input Stream is use to save file in to DataBase

			try {
				FileInputStream fileInputStream = null;

				// to create new file with actual name with extension
				File dstfile = new File(path, affInstBean.getFileUploadFileName());

				// to copy files at specified destination path
				FileUtils.copyFile(affInstBean.getFileUpload(), dstfile);

				// convert file into array of bytes
				bFile = new byte[(int) dstfile.length()];
				fileInputStream = new FileInputStream(dstfile);

				fileSize = fileInputStream.read(bFile);

				// fileinputStream must be close
				fileInputStream.close();
				affInstBean.setFilesByteSize(bFile);

				affInstBean.setFileSize(fileSize);

			} catch (java.lang.NullPointerException e) {
				isInserted = false;

			}
			session.beginTransaction();

			session.saveOrUpdate(affInstBean);

			session.getTransaction().commit();

			return affInstBean;

		} catch (Exception e) {

			e.printStackTrace();
			return affInstBean;
		} finally {

			// close session
			session.close();

		}

	}

	public List<AffBean> getAllCollege(List<Integer> ides) {
		log.info("list of Ides  " + ides);
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(AffBean.class);
		List<AffBean> affBeansList = criteria.add(Restrictions.in("instId", ides))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return affBeansList;
	}

	public ArrayList<AffBean> getInstitutes(String filterKey, String filterVal, ArrayList<Integer> idList,
			Date fromDate, Date toDate) {
		// Declarations
		ArrayList<AffBean> instList = new ArrayList<AffBean>();
		// Open session from session factory
		Session session = factory.openSession();
		try {
			Criteria InstSearchCr = session.createCriteria(AffBean.class);
			if (filterKey.contentEquals("NONE")) {
				log.info("Showing All Affiliated Institutes");
			}
			Iterator<AffBean> instIter = InstSearchCr.list().iterator();
			while (instIter.hasNext()) {
				instList.add(instIter.next());
			}

			return instList;
		} finally {
			// close session
			session.close();
		}

	}

	// delete()
	// getList()

	public Integer getRowCount() {
		// Declarations

		// Open session from session factory
		Session session = factory.openSession();
		try {
			Criteria c = session.createCriteria(AffBean.class);
			c.addOrder(Order.desc("instId"));
			c.setMaxResults(1);
			AffBean temp = (AffBean) c.uniqueResult();
			return temp.getInstId() + 1;

		} finally {
			// close session
			session.close();
		}

	}

	// get direct child i.e. college list
	public List<AffBean> getCollegesList() {
		log.info("child class method executing");
		Session session = factory.openSession();

		Criteria criteria = session.createCriteria(AffBean.class);
		criteria.addOrder(Order.asc("instName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AffBean> affBeansList = criteria.list();

		log.info("List size of college is ::" + affBeansList.size());
		session.close();
		return affBeansList;
	}

	// to get child college list based on university
	public ParBean getUniversityCollegeList(LoginBean loginBean) {

		log.info("parent class method executing");

		Session session = factory.openSession();

		Criteria criteria = session.createCriteria(ParBean.class);
		criteria.add(Restrictions.eq("parInstId", loginBean.getParBean().getParInstId()));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		ParBean parBean = (ParBean) criteria.list().iterator().next();
		session.close();
		return parBean;
	}

	public ArrayList<AffBean> getCollegesListByInstName(AffBean affBean) {

		Session session = factory.openSession();

		Criteria criteria = session.createCriteria(AffBean.class);
		criteria.add(Restrictions.eq("instName", affBean.getInstName()));

		ArrayList<AffBean> affBeansList = (ArrayList<AffBean>) criteria.list();
		session.close();
		return affBeansList;
	}

	// End of DAO Methods

	public AffBean viewInstDetail(Integer instId) {
		Session session = factory.openSession();

		Criteria criteria = session.createCriteria(AffBean.class);

		criteria.add(Restrictions.eq("instId", instId));
		AffBean affBean = (AffBean) criteria.list().iterator().next();
		session.close();
		return affBean;
	}

	public AffBean getOneCollegeRecord(Integer instId) {

		Session session = factory.openSession();

		AffBean affBean = (AffBean) session.get(AffBean.class, instId);
		session.close();
		return affBean;
	}

	public void updateCollege(AffBean newAffInstBean) {

		Session session = factory.openSession();

		try {
			Transaction tx = session.beginTransaction();
			AffBean oldBean = (AffBean) session.get(AffBean.class, newAffInstBean.getInstId());

			oldBean.setPlace(newAffInstBean.getPlace());
			oldBean.setInstName(newAffInstBean.getInstName());
			oldBean.setContactPerson(newAffInstBean.getContactPerson());
			oldBean.setContactNumber(newAffInstBean.getContactNumber());
			oldBean.setMobileNum(newAffInstBean.getMobileNum());
			oldBean.setEmail(newAffInstBean.getEmail());
			oldBean.setInstAddress(newAffInstBean.getInstAddress());

			session.merge(oldBean);

			tx.commit();
		} finally {
			session.close();
		}

	}

	public void updateCollegeDoc(AffBean newAffInstBean, String path) {

		Session session = factory.openSession();
		try {
			AffBean oldBean = (AffBean) session.get(AffBean.class, newAffInstBean.getInstId());
			// file input Stream is use to save file in to DataBase

			FileInputStream fileInputStream = null;

			// to create new file with actual name with extension
			File dstfile = new File(path, newAffInstBean.getFileUploadFileName());

			// to copy files at specified destination path
			FileUtils.copyFile(newAffInstBean.getFileUpload(), dstfile);

			// convert file into array of bytes
			byte[] bFile = new byte[(int) dstfile.length()];
			fileInputStream = new FileInputStream(dstfile);

			int fileSize = fileInputStream.read(bFile);

			// fileinputStream must be close
			fileInputStream.close();

			newAffInstBean.setFilesByteSize(bFile);
			newAffInstBean.setFileSize(fileSize);
			session.beginTransaction();

			oldBean.setFilesByteSize(newAffInstBean.getFilesByteSize());
			oldBean.setFileSize(newAffInstBean.getFileSize());
			oldBean.setFileUploadFileName(newAffInstBean.getFileUploadFileName());

			session.saveOrUpdate(oldBean);
			session.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			// close session
			session.close();

		}
	}

	// to check whether record is already exist or New
	public List<String> getCollegeNameList(String instName) {

		Session session = factory.openSession();

		Criteria criteria = session.createCriteria(AffBean.class);
		criteria.add(Restrictions.eq("instName", instName));
		criteria.setProjection(Projections.property("instName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<String> affList = criteria.list();
		session.close();
		return affList;

	}

	// to check whether record is already exist or New
	public List<AffBean> getCollegeNameFromDB(String instName) {

		Session session = factory.openSession();

		Criteria criteria = session.createCriteria(AffBean.class);
		criteria.add(Restrictions.eq("instName", instName));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AffBean> affList = criteria.list();
		session.close();
		return affList;
	}

	// to read excel file

	@SuppressWarnings("resource")
	public ArrayList<AffBean> importExcelFileToDatabase(String fileUploadFileName, File fileUpload, String path)
			throws Exception {
		// ArrayList<AffBean> notAddedCollegeList = new ArrayList<AffBean>();
		String instName, email = null, ContactPerson = null, instAddress = null, place = null;
		Integer contactNum = null, mobileNum = null;
		ArrayList<AffBean> affBeansList = new ArrayList<AffBean>();
		AffBean affBean = new AffBean();
		ArrayList<AffBean> totalCollegeList = new ArrayList<AffBean>();
		FileInputStream fileInputStream = new FileInputStream(fileUpload);

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

		XSSFSheet hssfSheet = xssfWorkbook.getSheetAt(0);

		Iterator<Row> rowIterator = hssfSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();

			if (row.getRowNum() == 0) {
				continue;
			}

			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();

				switch (cell.getCellType()) {

				case Cell.CELL_TYPE_STRING:

					break;
				case Cell.CELL_TYPE_NUMERIC:
					break;

				}
			}

			Cell r = row.getCell(0);
			instName = r.getStringCellValue();

			r = row.getCell(1);
			try {
				instAddress = r.getStringCellValue();
			} catch (java.lang.NullPointerException e) {

			}

			r = row.getCell(2);
			try {
				contactNum = (int) r.getNumericCellValue();
			} catch (java.lang.NullPointerException e) {

			}

			r = row.getCell(3);
			try {
				ContactPerson = r.getStringCellValue();
			} catch (java.lang.NullPointerException e) {

			}

			r = row.getCell(4);
			try {
				mobileNum = (int) r.getNumericCellValue();
			} catch (java.lang.NullPointerException e) {

			}

			r = row.getCell(5);
			try {
				email = r.getStringCellValue();
			} catch (java.lang.NullPointerException e) {

			}

			r = row.getCell(6);
			try {
				place = r.getStringCellValue();
			} catch (java.lang.NullPointerException e) {

			}

			affBean.setInstName(instName);
			affBean.setContactNumber(contactNum.toString());
			affBean.setMobileNum(mobileNum.toString());
			affBean.setEmail(email);
			affBean.setContactPerson(ContactPerson);
			affBean.setInstAddress(instAddress);
			affBean.setPlace(place);

			addBulkData(affBean);

		}

		return totalCollegeList;

	}

	// ---------------------------------------------------
	// to save record into Database
	public ArrayList<AffBean> addBulkData(AffBean affBean) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		ArrayList<AffBean> collegeListFromDB = new ArrayList<AffBean>();
		ArrayList<AffBean> notAddedCollegeList = new ArrayList<AffBean>();
		collegeListFromDB = getCollegesListByInstName(affBean);
		HttpSession httpSession = request.getSession();
		LoginBean loginBean = (LoginBean) httpSession.getAttribute("loginUserBean");
		String userprofile = httpSession.getAttribute("sesProfile").toString();

		if (collegeListFromDB.isEmpty()) {

			String username;

			// generate credentials for admin login
			try {
				username = "Inst".concat(affBean.getInstName().replaceAll("\\s+", "").substring(0, 4)
						.concat(getRowCount().toString()));

			} catch (java.lang.NullPointerException e) {
				username = "Inst".concat(affBean.getInstName().replaceAll("\\s+", "").substring(0, 4).concat("1"));

			}

			String password = RandomPasswordGenerator.generatePswd(6, 8, 1, 2, 0);
			PasswordEncryption.encrypt(password);
			String encryptedPwd = PasswordEncryption.encStr;

			// ------------------------------------------
			LoginBean creds = new LoginBean();
			creds.setPassword(encryptedPwd);
			creds.setUserName(username);

			String collegeProfile = "Admin";
			creds.setProfile(collegeProfile);
			affBean.setLoginBean(creds);
			creds.setProfile(collegeProfile);

			// -----------------------------------------

			// one to one Bidirectional relationship with login
			// for bidirectional relationship ,set parent record to child
			// record
			creds.setAffBean(affBean);
			// one to one relationship

			if (creds.getProfile().equals("Admin")) {
				affBean.setLoginBean(creds);
			}

			ParBean parBean1 = new ParBean();
			parBean1 = parDAO.viewUniversity(loginBean.getParBean().getParInstId());

			// one to may many to one relation ship
			affBean.setParBeanManyToOne(parBean1);
			parBean1.getAffBeanOneToManySet().add(affBean);
			// -------------------
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(affBean);
			tx.commit();
			session.close();

			EmailSessionBean email = new EmailSessionBean();
			email.sendEmail(affBean.getEmail(), "Welcome To Fee Collection Portal!", username, password,
					affBean.getInstName());
		}
		return notAddedCollegeList;
	}

	public AffBean getCollegeDues(Integer collegeId) {
		Session session = factory.openSession();
		AffBean affBean = (AffBean) session.get(AffBean.class, collegeId);
		session.close();
		return affBean;
	}

	public List<TransactionBean> getTransactionDetails(Integer instituteId, String superAdmin) {
		List<TransactionBean> transactionDetails = null;
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(TransactionBean.class);
		if (superAdmin.contentEquals("SU")) {
			transactionDetails = criteria.list();
		} else {
			transactionDetails = criteria.add(Restrictions.eq("insId", instituteId)).list();

		}

		return transactionDetails;
	}

	public List<AffBean> getAllTransactionRecordsForSU() {
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(AffBean.class);
		List<AffBean> collegesTransactionList = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return collegesTransactionList;

	}

	public List<TransactionBean> getAllTransactionRecordsForSuper() {
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(TransactionBean.class);
		List<TransactionBean> collegesTransactionList = criteria.list();
		session.close();
		return collegesTransactionList;

	}

	public Double[] getTotalDuesAndPaymentDoneOfCollege(Integer collegeId) {
		Double[] netDueAndPaymentDone=new Double[2];
		Double paymentToDate=0.0;
		Double netDues=0.0;
		Session session = factory.openSession();
		try{
		AffBean affBean = (AffBean) session.get(AffBean.class, collegeId);
		List<AffFeePropBean> dueList = new ArrayList<AffFeePropBean>(affBean.getFeeProps());
		Iterator<AffFeePropBean> itr = dueList.iterator();
		while (itr.hasNext()) {
			PaymentDuesBean dues = itr.next().getDueBean();
			Double netDuesFromDB = dues.getNetDue() == null ? 0.0 : dues.getNetDue();
			netDues = netDues + netDuesFromDB;
			log.info("Total Net Dues" + netDues);
			Double paymentToDateFromDB = dues.getPayments_to_date() == null ? 0.0 : dues.getPayments_to_date();
			paymentToDate = paymentToDate + paymentToDateFromDB;
			log.info("Payment to date Net Dues" + paymentToDate);
		}
		}catch(Exception ex)
		{
		return netDueAndPaymentDone;
		}
		netDueAndPaymentDone[0]=netDues;
		netDueAndPaymentDone[1]=paymentToDate;
        return netDueAndPaymentDone;
	
	}

}
