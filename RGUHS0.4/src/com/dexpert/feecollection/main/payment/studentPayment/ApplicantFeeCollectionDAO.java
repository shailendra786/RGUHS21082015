package com.dexpert.feecollection.main.payment.studentPayment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.dexpert.feecollection.challan.TransactionBean;
import com.dexpert.feecollection.main.ConnectionClass;
import com.dexpert.feecollection.main.fee.PaymentDuesBean;
import com.dexpert.feecollection.main.fee.lookup.LookupDAO;
import com.dexpert.feecollection.main.users.applicant.AppBean;

public class ApplicantFeeCollectionDAO {
	// Global Variables
	public static SessionFactory factory = ConnectionClass.getFactory();
	static Logger log = Logger.getLogger(LookupDAO.class.getName());

	public ApplicantFeeCollectionBean calculateTotalFee(ApplicantFeeCollectionBean fc) {

		ApplicantFeeCollectionBean beanlist = new ApplicantFeeCollectionBean();
		Session session = factory.openSession();
		try {

			Criteria criteria = session.createCriteria(ApplicantFeeCollectionBean.class);
			criteria.add(Restrictions.eq("service_type", fc.getService_type()))
					.add(Restrictions.eq("course", fc.getCourse())).add(Restrictions.eq("faculty", fc.getFaculty()))
					.add(Restrictions.eq("nationality", fc.getNationality()));
			beanlist = (ApplicantFeeCollectionBean) criteria.list().iterator().next();

			return beanlist;
		} finally {
			session.close();

		}

	}

	public Map<String, String> serviceTypeList() {

		Map<String, String> map = new HashMap<String, String>();
		List<ServiceTypeBean> list = new ArrayList<ServiceTypeBean>();

		map.put("EC", "ELIGIBILITY CRITERIA");
		map.put("ECR", "ELIGIBILITY CERTIFICATE RENEWAL");
		map.put("CONAIE", "CHANGE OF NAME & INITIAL EXPANSION");
		map.put("MC", "MIGRATION CERTIFICATE");
		map.put("MT", "MIGRATION TRANSFER 1ST TO 2ND YEAR ");
		map.put("NOCIT", "NOC(for Internship Transfer)");
		map.put("CMCL", "CONSOLIDATED MARKS CARD (LAMINATED)");
		map.put("NCIMC", "NAME CORRECTION IN MARKS CARD");
		map.put("PDC", "PDC");
		map.put("RC", "RANK CERTIFICATE");
		map.put("MPC", "MEDAL/PRIZE CERTIFICATE");
		map.put("MPCD", "MEDAL/PRIZE sCERTIFICATE-Duplicate");
		map.put("DDC", "DUPLICATE DEGREE CERTIFICATE");
		map.put("PGETS", "PGET Superspeciality");
		map.put("PGETF", "PGET Fee");
		map.put("PGETA", "PGET Application");
		map.put("VO", "VERIFICATION ONLY");
		map.put("VACOC", "VERIFICATION AND CERTIFICATION OF COPY'S");
		map.put("CCOTSO", "CERTIFIED COPY OF THE SYLLABUS/ORINANCE");
		map.put("DMC", "DUPLICATE MARKS CARD");
		map.put("PPC", "PROVISIONAL PASS CERTIFICATE");
		map.put("DC", "DEGREE CERTIFICATE");
		map.put("CC", "CERTIFICATE COURSE");
		map.put("NCIDC", "NAME CORRECTION IN DEGREE CERTIFICATE");
		map.put("COTOTP", "CHANGE OF TITLE OF THESIS-PHD");
		map.put("RRP", "RE-REGISTRATION-PH.D");
		map.put("EOR", "EXTENSION OF REGISTRATION");
		map.put("PE", "PRE EXAMINATION");
		map.put("RF", "REGISTRATION FEE");
		map.put("FSSAFPE", "FINAL SYNOPSIS SUBMISSION AND FINAL PH.D. EXAMINATION");
		map.put("DF", "DISSERTATION FEE");
		map.put("DFR", "DISSERTATION FEE-RESUBMISSION");
		map.put("CVOTAOTDF", "CREDENTIAL VERIFICATION / OFFICIAL TRANSCRIPT/ ATTESTATION OF THE DOCUMENTS FEE");
		map.put("MOIEC", "MEDIUM OF INSTRUCTION IN ENGLISH CERTIFICATION");
		map.put("MLCPFR2500", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 2500");
		map.put("MLCPFR5000", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 5000");
		map.put("MLCPFR7500", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 7500");
		map.put("MLCPFR10000", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 10000");
		map.put("MLCPFR12500", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 12500");
		map.put("MLCPFR17500", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 17500");
		map.put("MLCPFR20000", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 20000");
		map.put("MLCPFR25000", "MALPRACTICE LAPSES COMMITTEE - PENALTY FEE-RS. 25000");
		map.put("RTFS1", "RE-TOTALING FOR 1 SUBJECT");
		map.put("RTFS2", "RE-TOTALING FOR 2 SUBJECT");
		map.put("RTFS3", "RE-TOTALING FOR 3 SUBJECT");
		map.put("RTFS4", "RE-TOTALING FOR 4 SUBJECT");
		map.put("RTFS5", "RE-TOTALING FOR 5 SUBJECT");
		map.put("PMCFFR5000", "PROFESSIONAL MISCONDUCT COMMITTEE FINE FEES-RS. 5000");
		map.put("PMCFFR1000", "PROFESSIONAL MISCONDUCT COMMITTEE FINE FEES-RS. 10000");
		map.put("PMCFFR20000", "PROFESSIONAL MISCONDUCT COMMITTEE FINE FEES-RS. 20000");
		map.put("PMCFFR30000", "PROFESSIONAL MISCONDUCT COMMITTEE FINE FEES-RS. 30000");
		map.put("PMCFFR50000", "PROFESSIONAL MISCONDUCT COMMITTEE FINE FEES-RS. 50000");
		map.put("PMCFFR100000", "PROFESSIONAL MISCONDUCT COMMITTEE FINE FEES-RS. 100000");

		ServiceTypeBean serviceTypeBean = new ServiceTypeBean();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			serviceTypeBean.setServiceKey(entry.getKey());
			serviceTypeBean.setServiceValue(entry.getValue());
		}

		return map;

	}

	public Map<String, String> facultyList() {

		Map<String, String> map = new HashMap<String, String>();

		Session session = factory.openSession();

		map.put("MED", "MEDICAL");
		map.put("DENT", "DENTAL");
		map.put("NursPHYSIOPHARM", "NURSING,PHYSIOTHERAPY,PHARMACY");
		map.put("AYAVRDHOMEOUNANYOGA", "AYUSH,AYURVEDA,HOMEOPATHY,UNANI,YOGA");
		map.put("PMEDIAOTHERS", "PARAMEDICAL AND Others");

		FacultyBean facultyBean = new FacultyBean();

		for (Map.Entry<String, String> entry : map.entrySet()) {

			facultyBean.setFacultyKey(entry.getKey());
			facultyBean.setFacultyValue(entry.getValue());

		}
		return map;

	}

	public Map<String, String> nationalityList() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("INDIAN", "INDIAN");
		map.put("NRISAARC", "NRI/SAARC");
		map.put("FOREIGN", "FOREIGN");

		NationalityBean nationalityBean = new NationalityBean();
		for (Map.Entry<String, String> entry : map.entrySet()) {

			nationalityBean.setNationalityKey(entry.getKey());
			nationalityBean.setNationalityValue(entry.getValue());

		}
		return map;

	}

	public Map<String, String> courseList() {

		CourseBean courseBean = new CourseBean();
		Map<String, String> map = new HashMap<String, String>();

		map.put("PG", "PG");
		map.put("UG", "UG");
		map.put("PGDIPLOMA", "PG DIPLOMA");
		map.put("PHD", "PHD");

		for (Map.Entry<String, String> entry : map.entrySet()) {
			Session session = factory.openSession();
			courseBean.setCourseKey(entry.getKey());
			courseBean.setCourseValue(entry.getValue());
			Transaction tx = session.beginTransaction();
			session.save(courseBean);
			tx.commit();
			session.close();
		}
		return map;

	}

	public void updateFeeduesTableDetail(String dueString,String enrollmentId,Integer insId) {
		String []feeIdAndAmount=dueString.split("~");
		Session session=factory.openSession();
        Criteria criteria=session.createCriteria(PaymentDuesBean.class);
	    criteria.add(Restrictions.eq("feeId",feeIdAndAmount[0])).add(Restrictions.eq("affBean.instId",insId));
	    PaymentDuesBean paymentDue=(PaymentDuesBean)criteria.list().iterator().next();
	    paymentDue.setNetDue((paymentDue.getNetDue()-Double.parseDouble(feeIdAndAmount[1])));
	    paymentDue.setPayments_to_date(paymentDue.getPayments_to_date()==null?0.0d:paymentDue.getPayments_to_date()+Double.parseDouble(feeIdAndAmount[1]));
	    Transaction transaction=session.beginTransaction();
	    session.merge(paymentDue);
	    transaction.commit();
	    session.close();
	}
	public void updateTransactionStatus(String transId,String transStatus,String paymentMode )
	{
	Session session=factory.openSession();	
	Criteria criteria=session.createCriteria(TransactionBean.class);	
	TransactionBean transactionBean=(TransactionBean)criteria.add(Restrictions.eq("txnId",transId)).list().iterator().next();	
	if(transStatus!=null){
	transactionBean.setStatus(transStatus);	
	}
	transactionBean.setPaymentMode(paymentMode);
	Transaction tran=session.beginTransaction();
	session.merge(transactionBean);
	tran.commit();
	session.close();
	
	}

	public void saveStudentTransaction(TransactionBean transaction) {
		Session session = factory.openSession();
		try {

			Date transDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dt = sdf.format(transDate);
            Transaction tx = session.beginTransaction();
			session.save(transaction);
			tx.commit();

		} finally {
			session.close();
			// TODO: handle exception
		}

	}

	public void insertPaymentDetails(TransactionBean transaction) {
		Session session = factory.openSession();
		Transaction hibernateTran = session.beginTransaction();
		session.save(transaction);
		hibernateTran.commit();
		session.close();
	}
	
	public void updateTransTable(String txnId, String RspCode, String dueStr, String studentEnrollmentNo, String payMode) {
		TransactionBean newBean = new TransactionBean();
		newBean.setTxnId(txnId);
		Session session = factory.openSession();

		log.info("DUe String is ::" + dueStr);
		try {
			log.info("updateTransTable method :");
			TransactionBean oldBean = (TransactionBean) session.get(TransactionBean.class, txnId);

			if (RspCode.equals("0")) {
				Transaction transaction = session.beginTransaction();

				oldBean.setStatus("Paid");

				session.merge(oldBean);
				transaction.commit();

				if (dueStr.contains("!")) {
					log.info("If ::::::::::::::::");
					String collectionOfFeeIdAndAmount[] = dueStr.split("!");
					log.info("collection Array 11111" + collectionOfFeeIdAndAmount[0]);
					log.info("Size of comple Array" + collectionOfFeeIdAndAmount.length);
					for (String singleFeeIdAndAmount : collectionOfFeeIdAndAmount) {

						String feeIdAndAmount[] = singleFeeIdAndAmount.split("~");
						log.info("Array size after spiting by ~" + feeIdAndAmount[0]);
						log.info("Fee Id AMount is ::" + feeIdAndAmount[0]);
						log.info("Enrollment  is ::" + studentEnrollmentNo);

						SQLQuery sqlQuery = session
								.createSQLQuery("SELECT * FROM sgi.fee_dues_master where feeId=:feeId and enrollmentNumber_Fk=:enroll");
						sqlQuery.setParameter("feeId", feeIdAndAmount[0]);
						sqlQuery.setParameter("enroll", studentEnrollmentNo);
						sqlQuery.addEntity(PaymentDuesBean.class);
						PaymentDuesBean paymentDue = (PaymentDuesBean) sqlQuery.list().iterator().next();
						Transaction tran = session.beginTransaction();
						paymentDue.setNetDue((paymentDue.getNetDue() - Double.parseDouble(feeIdAndAmount[1])));
						Double paymentToDate = paymentDue.getPayments_to_date() == null ? 0.0d : paymentDue
								.getPayments_to_date();
						log.info("payment to date" + paymentToDate);
						paymentDue.setPayments_to_date(paymentToDate + Double.parseDouble(feeIdAndAmount[1]));
						session.merge(paymentDue);
						tran.commit();
					}

				}

				else {

					String feeIdAndAmount[] = dueStr.split("~");
					SQLQuery sqlQuery = session
							.createSQLQuery("SELECT * FROM sgi.fee_dues_master where feeId=:feeId and enrollmentNumber_Fk=:enroll");
					sqlQuery.setParameter("feeId", feeIdAndAmount[0]);
					sqlQuery.setParameter("enroll", studentEnrollmentNo);
					sqlQuery.addEntity(PaymentDuesBean.class);
					PaymentDuesBean paymentDue = (PaymentDuesBean) sqlQuery.list().iterator().next();
					Transaction tran = session.beginTransaction();
					paymentDue.setNetDue((paymentDue.getNetDue() - Double.parseDouble(feeIdAndAmount[1])));
					paymentDue.setPayments_to_date(paymentDue.getPayments_to_date() == null ? 0.0d : paymentDue
							.getPayments_to_date() + Double.parseDouble(feeIdAndAmount[1]));
					session.merge(paymentDue);
					tran.commit();

				}

			} else {
				Transaction transaction = session.beginTransaction();
				oldBean.setStatus("Declined");
				session.merge(oldBean);
				transaction.commit();
			}

		} finally {

			session.close();
		}

	}

}
