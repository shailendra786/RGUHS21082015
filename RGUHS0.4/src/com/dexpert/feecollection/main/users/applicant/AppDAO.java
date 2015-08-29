package com.dexpert.feecollection.main.users.applicant;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import COM.rsa.Intel.cr;

import com.dexpert.feecollection.main.ConnectionClass;
import com.dexpert.feecollection.main.communication.email.EmailSessionBean;
import com.dexpert.feecollection.main.users.LoginBean;
import com.dexpert.feecollection.main.users.PasswordEncryption;
import com.dexpert.feecollection.main.users.RandomPasswordGenerator;
import com.dexpert.feecollection.main.users.affiliated.AffBean;
import com.dexpert.feecollection.main.users.affiliated.AffDAO;

public class AppDAO {

	// Declare Global Variables Here
	public static SessionFactory factory = ConnectionClass.getFactory();
	static Logger log = Logger.getLogger(AppDAO.class.getName());
	AffDAO aff = new AffDAO();

	// End of Global Variables

	// ---------------------------------------------------

	// DAO Methods Here

	public AppBean saveOrUpdate(AppBean appBean, Integer aplInstId) {
		// Declarations
		// Open session from session factory
		Session session = factory.openSession();
		AffBean affBean = new AffBean();

		// to get college record based on id to create relationship
		affBean = aff.viewInstDetail(aplInstId);
		
		// one to Many bidirectional
		affBean.setAppBean(appBean);
		appBean.setAffBeanManyToOne(affBean);
		

		// one to many Relationship
		affBean.getAplBeanSet().add(appBean);

		try {
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(appBean);
			tx.commit();

		} finally {

			session.close();
		}
		return appBean;

	}

	public List<AppBean> getAllStudentList() {
		Session session = factory.openSession();
		try {
			Criteria criteria = session.createCriteria(AppBean.class);
			List<AppBean> list = criteria.list();
			return list;
		} finally {
			session.close();
		}

	}

	public AppBean viewApplicantDetail(Integer appId) {
		Session session = factory.openSession();
		try {

			Criteria criteria = session.createCriteria(AppBean.class);
			criteria.add(Restrictions.eq("aplId", appId));
			AppBean appBean = (AppBean) criteria.list().iterator().next();
			return appBean;

		} finally {
			session.close();
			// TODO: handle exception
		}

	}

	public AffBean getStudentDetail(LoginBean bean) {
		Session session = factory.openSession();
		try {

			Integer id = bean.getAffBean().getInstId();
			Criteria criteria = session.createCriteria(AffBean.class);

			criteria.add(Restrictions.eq("instId", id));

			AffBean affBean = (AffBean) criteria.list().iterator().next();

			log.info("List is ::" + affBean.getAplBeanSet().size());
			log.info("List is ::" + affBean.getEmail());
			return affBean;

		} finally {
			session.close();
			// TODO: handle exception
		}
	}

	public List<String> existingEnrollNum(AppBean appBean) {
		Session session = factory.openSession();
		try {
			Criteria criteria = session.createCriteria(AppBean.class);
			criteria.setProjection(Projections.property("enrollmentNumber"));
			criteria.add(Restrictions.eq("enrollmentNumber", appBean.getEnrollmentNumber()));
			List<String> list = criteria.list();
			return list;

		} finally {
			session.close();
			// TODO: handle exception
		}

	}

	public List<AppBean> getStudentDetailByEnrollMentNumber(String enrollmentNumber) {
		Session session = factory.openSession();

		try {

			Criteria criteria = session.createCriteria(AppBean.class);
			criteria.add(Restrictions.eq("enrollmentNumber", enrollmentNumber));

			List<AppBean> appBeanList = criteria.list();
			return appBeanList;

		} finally {
			session.close();
			// TODO: handle exception
		}

	}

	public AppBean getUserDetail(String EnrId) {

		Session session = factory.openSession();

		try {

			Criteria criteria = session.createCriteria(AppBean.class);
			criteria.add(Restrictions.eq("enrollmentNumber", EnrId));

			AppBean appBeanList=null;
			try {
				appBeanList = (AppBean) criteria.list().iterator().next();
			} catch (java.util.NoSuchElementException e) {
				appBeanList=null;
			}
			return appBeanList;

		} finally {
			session.close();
			// TODO: handle exception
		}
	}

	public ArrayList<AppBean> importExcelFileToDatabase(String fileUploadFileName, File fileUpload, String path)
			throws Exception {

		String enrolNo, aplName, aplLstName, aplGender, emailAddress, aplQuota, aplCategory, address, instName;
		Integer mobileNumPri, MobileNumSec;
		ArrayList<AppBean> appBeansList = new ArrayList<AppBean>();
		AppBean appBean = new AppBean();
		AffDAO affDAO = new AffDAO();
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

				case Cell.CELL_TYPE_NUMERIC:

					break;

				case Cell.CELL_TYPE_STRING:

					break;

				}
			}

			Cell r = row.getCell(0);
			enrolNo = r.getStringCellValue();

			r = row.getCell(1);
			aplName = r.getStringCellValue();

			r = row.getCell(2);
			aplLstName = r.getStringCellValue();

			r = row.getCell(3);
			aplGender = r.getStringCellValue();

			r = row.getCell(4);
			mobileNumPri = (int) r.getNumericCellValue();

			r = row.getCell(5);
			MobileNumSec = (int) r.getNumericCellValue();

			r = row.getCell(6);
			emailAddress = r.getStringCellValue();

			r = row.getCell(7);
			address = r.getStringCellValue();

			r = row.getCell(8);
			instName = r.getStringCellValue();

			r = row.getCell(8);
			aplQuota = r.getStringCellValue();

			r = row.getCell(9);
			aplCategory = r.getStringCellValue();

			appBean.setAplFirstName(aplName);
			appBean.setAplLstName(aplLstName);
			appBean.setAplAddress(address);
			appBean.setAplEmail(emailAddress);
			appBean.setAplMobilePri(mobileNumPri.toString());
			appBean.setAplMobileSec(MobileNumSec.toString());
			appBean.setGender(aplGender);
			appBean.setEnrollmentNumber(enrolNo);
			/*
			 * List<AffBean> lst = affDAO.getCollegeNameFromDB(instName); if
			 * (!(lst.isEmpty())) {
			 */
			// Iterator<AffBean> iterator = lst.iterator();

			// while (iterator.hasNext()) {
			// AffBean affBean = (AffBean) iterator.next();
			// appBean.setAplId(affBean.getInstId());

			// }

			// }

			appBeansList.add(appBean);

			addBulkData(appBean);

			// }
			// notAddedCollegeList.add(affBean);

		}
		return null;

	}

	// ---------------------------------------------------

	// to save record into Database
	public ArrayList<AppBean> addBulkData(AppBean appBean) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidAlgorithmParameterException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {

		List<AppBean> studentFromDBList = getStudentDetailByEnrollMentNumber(appBean.getEnrollmentNumber());

		if (studentFromDBList.isEmpty()) {
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(appBean);
			tx.commit();
			session.close();

		}

		return null;
	}

}
