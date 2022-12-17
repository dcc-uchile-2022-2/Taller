package org.tds.sgh.test.stubs;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;


public class DataAccessConnection
{
	// --------------------------------------------------------------------------------------------
	
	private Session session;
	
	private Transaction transaction;
	
	// --------------------------------------------------------------------------------------------
	
	public DataAccessConnection(Session session)
	{
		this.session = session;
	}
	
	// --------------------------------------------------------------------------------------------
	
	public void beginTx()
	{
		this.transaction = this.session.beginTransaction();
	}
	
	public void close()
	{
		this.session.close();
	}
	
	public void commitTx()
	{
		this.transaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz)
	{
		@SuppressWarnings("rawtypes")
		List objlist = this.session.createCriteria(clazz).list();
		
		if (objlist.isEmpty())
		{
			return null;
		}
		else
		{
			return (T) objlist.get(0);
		}
	}
	
	public <T> int getCount(Class<T> clazz)
	{
		return (int) this.session.createCriteria(clazz).setProjection(Projections.rowCount()).uniqueResult();
	}
	
	public void rollbackTx()
	{
		this.transaction.rollback();
	}
	
	public void save(Object o)
	{
		this.session.save(o);
	}
}
