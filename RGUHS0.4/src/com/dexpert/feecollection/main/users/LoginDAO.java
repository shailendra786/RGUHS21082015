package com.dexpert.feecollection.main.users;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.dexpert.feecollection.main.ConnectionClass;
import com.dexpert.feecollection.main.users.affiliated.AffDAO;

public class LoginDAO {

	// Declare Global Variables Here
	public static SessionFactory factory = ConnectionClass.getFactory();
	static Logger log = Logger.getLogger(AffDAO.class.getName());

	// End of Global Variables

	// ---------------------------------------------------

	// DAO Methods Here
	// method to getDetails About user
	public List<LoginBean> getLoginDetails(LoginBean loginBean) {
		Session session = factory.openSession();
		try {

			Criteria criteria = session.createCriteria(LoginBean.class);
			criteria.add(Restrictions.eq("userName", loginBean.getUserName()));

			List<LoginBean> bean = criteria.list();

			return bean;
		} finally {
			session.close();
		}
	}

}
