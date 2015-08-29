package com.dexpert.feecollection.main.users.parent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.dexpert.feecollection.main.ConnectionClass;
import com.dexpert.feecollection.main.users.affiliated.AffBean;
import com.dexpert.feecollection.main.users.affiliated.AffDAO;

public class ParDAO {

	// Declare Global Variables Here
	public static SessionFactory factory = ConnectionClass.getFactory();
	static Logger log = Logger.getLogger(AffDAO.class.getName());

	// End of Global Variables

	// ---------------------------------------------------

	// DAO Methods Here

	ParBean parBean = new ParBean();

	// method to get max row count of table
	public Integer getRowCount() {

		// Declarations

		// Open session from session factory
		Session session = factory.openSession();
		try {
			Criteria c = session.createCriteria(ParBean.class);
			c.addOrder(Order.desc("parInstId"));
			c.setMaxResults(1);
			ParBean temp = (ParBean) c.uniqueResult();

			return temp.getParInstId() + 1;

		} finally {
			// close session
			session.close();
		}

	}

	public List<Integer> getIdesOfAllCollege(Integer id) {
		Session session = factory.openSession();
		try {
			List<Integer> listOfIdes = new ArrayList<Integer>();

			Criteria criteria = session.createCriteria(ParBean.class);
			ParBean parBean = (ParBean) criteria.add(Restrictions.eq("parInstId", id)).list().iterator().next();
			Set<AffBean> affBean = parBean.getAffBeanOneToManySet();
			Iterator<AffBean> itr = affBean.iterator();
			while (itr.hasNext()) {
				listOfIdes.add(itr.next().getInstId());
			}
			log.info("list size is" + listOfIdes.size());
			return listOfIdes;
		} finally {
			session.close();
			// TODO: handle exception
		}

	}

	// method to register record of university
	public ParBean saveOrUpdate(ParBean parBean, String path) throws IOException {
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
				File dstFile = new File(path, parBean.getFileUploadFileName());

				// to copy files at specified destination path
				FileUtils.copyFile(parBean.getFileUpload(), dstFile);

				// convert file into array of bytes

				bFile = new byte[(int) dstFile.length()];
				fileInputStream = new FileInputStream(dstFile);

				fileSize = fileInputStream.read(bFile);
				// fileinputStream must be close
				fileInputStream.close();
			} catch (java.lang.NullPointerException e) {

			}

			parBean.setFilesByteSize(bFile);

			parBean.setFileSize(fileSize);
			session.beginTransaction();
			session.saveOrUpdate(parBean);
			session.getTransaction().commit();
			return parBean;

		} finally {

			// close session
			session.close();

		}
	}

	public List<ParBean> getUniversityList() {
		Session session = factory.openSession();
		try {

			Criteria criteria = session.createCriteria(ParBean.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<ParBean> list = criteria.list();
			return list;

		} finally {
			session.close();

		}
	}

	public ParBean viewUniversity(Integer id) {
		Session session = factory.openSession();

		log.info("View University");
		try {
			Criteria criteria = session.createCriteria(ParBean.class);
			criteria.add(Restrictions.eq("parInstId", id));
			ParBean bean = (ParBean) criteria.list().iterator().next();
			return bean;
		} finally {
			session.close();

		}

	}

}
