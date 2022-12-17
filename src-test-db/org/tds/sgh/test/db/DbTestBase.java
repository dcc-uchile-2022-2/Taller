package org.tds.sgh.test.db;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.infrastructure.Infrastructure;
import org.tds.sgh.system.ControllerFactory;
import org.tds.sgh.test.TestBase;
import org.tds.sgh.test.stubs.CalendarioStub;
import org.tds.sgh.test.stubs.DataAccess;
import org.tds.sgh.test.stubs.DataAccessConnection;
import org.tds.sgh.test.stubs.SistemaFacturacionStub;
import org.tds.sgh.test.stubs.SistemaMensajeriaStub;


@RunWith(JUnit4.class)
public abstract class DbTestBase extends TestBase
{
	// --------------------------------------------------------------------------------------------
	
	protected DataAccessConnection cnx;
	
	// --------------------------------------------------------------------------------------------
	
	@Before
	@Override
	public void setUp() throws Exception
	{
		calendario = new CalendarioStub(c -> calendarioCambiarHoy = c);
		
		sistemaMensajeria = new SistemaMensajeriaStub();
		
		sistemaFacturacion = new SistemaFacturacionStub();
		
		Infrastructure.configure(sistemaFacturacion, sistemaMensajeria, calendario);
		
		cnx = DataAccess.getInstance().createConnection();
		
		cnx.beginTx();
		
		cadenaHotelera = crearCadenaHotelera();
		
		controllerFactory = new ControllerFactory(cadenaHotelera);
	}
	
	@After
	@Override
	public void tearDown() throws Exception
	{
		cnx.save(cadenaHotelera);
		
		cnx.commitTx();
		
		super.tearDown();
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	protected CadenaHotelera crearCadenaHotelera()
	{
		long last = cnx.getCount(CadenaHotelera.class);
		
		CadenaHotelera cadenaHotelera = new CadenaHotelera("cadena-" + (last + 1));
		
		return cadenaHotelera;
	}
}
