package com.dexpert.feecollection.main.fee.config;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.dexpert.feecollection.main.ConnectionClass;
import com.dexpert.feecollection.main.fee.lookup.LookupDAO;

public class FcDAO {
	// Global Declarations
	public static SessionFactory factory = ConnectionClass.getFactory();
	static Logger log = Logger.getLogger(LookupDAO.class.getName());

	// Global Declarations End
	// DAO Methods
	public void insertFeeBulk(ArrayList<FcBean> savelist) {
		// Declarations

		// Open session from session factory
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			for (int i = 0; i < savelist.size(); i++) {
				FcBean savebean = new FcBean();
				savebean = savelist.get(i);
				session.save(savebean);
				if (i % 20 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// close session
			session.close();
		}
	}

	public void saveFeeDetails(FeeDetailsBean savedata) {
		// Declarations

		// Open session from session factory
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(savedata);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// close session
			session.close();
		}
	}

	public ArrayList<FeeDetailsBean> GetFees(String filterKey, String filterValue, Integer id, ArrayList<Integer> ids) {
		// Declarations
		ArrayList<FeeDetailsBean> ResultList = new ArrayList<FeeDetailsBean>();
		// Open session from session factory
		Session session = factory.openSession();
		try {
			Criteria feeCr = session.createCriteria(FeeDetailsBean.class);
			if (filterKey.contentEquals("ALL")) {

			} else if (filterKey.contentEquals("payee")) {
				if (filterValue.contentEquals("applicant")) {
					feeCr.add(Restrictions.eq("forApplicant", 1));

				}
				if (filterValue.contentEquals("institute")) {
					feeCr.add(Restrictions.eq("forInstitute", 1));
				}
			} else if (filterKey.contentEquals("name")) {
				feeCr.add(Restrictions.eq("feeName", filterValue));
			} else if (filterKey.contentEquals("id")) {
				feeCr.add(Restrictions.eq("feeId", id));
			} else if (filterKey.contentEquals("ids")) {
				feeCr.add(Restrictions.in("feeId", ids));
			}
			feeCr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			Iterator<FeeDetailsBean> feeIt = feeCr.list().iterator();
			while (feeIt.hasNext()) {
				ResultList.add(feeIt.next());
			}
			return ResultList;

		} catch (Exception e) {
			e.printStackTrace();
			return ResultList;

		} finally {
			// close session
			session.close();
		}
	}

	// 786
	public Integer getFeeIdByName(String feeName) {
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(FeeDetailsBean.class);
		criteria.add(Restrictions.eq("feeName", feeName)).setProjection(Projections.property("feeId"));
		Integer feeId = (Integer) criteria.list().iterator().next();
		session.close();
		return feeId;

	}
	// DAO Methods End
}
