package com.dexpert.feecollection.main.fee.lookup.values;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.dexpert.feecollection.main.ConnectionClass;

public class FvDAO {
	//Global Declarations
	public static SessionFactory factory = ConnectionClass.getFactory();
	static Logger log = Logger.getLogger(FvDAO.class.getName());	
	//End of Global Declarations
	
	//DAO Methods Start
	public FvBean saveParamValues(FvBean valuesData)
	{
		//Declarations

		//Open session from session factory
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(valuesData);
			session.getTransaction().commit();
			return valuesData;
		} finally {
			//close session
			session.close();
		}
	}
	
	public ArrayList<FvBean> getValues(String filterKey,String filterValue, ArrayList<Integer>Ids)
	{
		//Declarations
		ArrayList<FvBean>resultList=new ArrayList<FvBean>();
		
				//Open session from session factory
				Session session = factory.openSession();
				try {
					Criteria searchCr=session.createCriteria(FvBean.class);
					if(filterKey.contentEquals("ALL"))
					{
						
					}
					if(filterKey.contentEquals("Ids"))
					{
						searchCr.add(Restrictions.in("feeValueId", Ids));
					}
					if(filterKey.contentEquals("LookupIds"))
					{
						searchCr.add(Restrictions.in("FeeLookupId_Fk", Ids));
					}
					resultList=(ArrayList<FvBean>) searchCr.list();
					return resultList;
				} finally {
					//close session
					session.close();
				}

	}
	
	//DAO Methods End
	
}
