package org.tds.sgh.test.stubs;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


public class DataAccess
{
	// --------------------------------------------------------------------------------------------
	
	private static final Logger log = Logger.getLogger(DataAccess.class);
	
	// --------------------------------------------------------------------------------------------
	
	private static DataAccess Instance;
	
	// --------------------------------------------------------------------------------------------
	
	public static DataAccess getInstance()
	{
		if (Instance == null)
		{
			Instance = new DataAccess();
		}
		
		return Instance;
	}
	
	// --------------------------------------------------------------------------------------------
	
	private SessionFactory sessionFactory;
	
	// --------------------------------------------------------------------------------------------
	
	private DataAccess()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	
	public DataAccessConnection createConnection()
	{
		this.start();
		
		return new DataAccessConnection(this.sessionFactory.openSession());
	}
	
	public void shutdown()
	{
		this.sessionFactory.close();
	}
	
	public void start()
	{
		if (this.sessionFactory == null)
		{
			try
			{
				this.sessionFactory = new AnnotationConfiguration()
					.configure()
					.buildSessionFactory();
			}
			catch (Throwable t)
			{
				log.error("Cannot access the database", t);
				
				t.printStackTrace();
				
				throw new ExceptionInInitializerError(t);
			}
		}
	}
}
