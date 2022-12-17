package org.tds.sgh.test.db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;


@RunWith(JUnit4.class)
public class CadenaTiposHabitacionDbTest extends DbTestBase
{
	@Test
	public void TiposHabitacionNoEsNull()
	{
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		assertTrue(
			"El controlador devolvió null. Debe devolver una lista de tipos de habitación.",
			controller.getTiposHabitacion() != null);
	}
	
	@Test
	public void TiposHabitacionEsVacio()
	{
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		assertTrue(
			"El controlador no devolvió una lista de tipos de habitación vacía al inicio.",
			controller.getTiposHabitacion().isEmpty());
	}
	
	@Test
	public void CadenaRegistraTipoHabitacion() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		List<TipoHabitacionDTO> tiposHabitacion = asList(controller.getTiposHabitacion());
		
		assertTrue(
			"El sistema no registró al tipo de habitación",
			!tiposHabitacion.isEmpty());
		
		assertTrue(
			"El sistema registró más de un tipo de habitación.",
			tiposHabitacion.size() == 1);
		
		TipoHabitacionDTO tipoHabitacionX = tiposHabitacion.get(0);
		
		assertTrue(
			"El sistema devolvió null, debe devolver los datos del tipo de habitación registrado.",
			tipoHabitacionX != null);
		
		assertTrue(
			"El sistema reutilizó instancias de test; el sistema debe preservar su propia información.",
			tipoHabitacionX != tipoHabitacion1);
		
		assertTrue(
			"El sistema registró datos del tipo de habitación que no coinciden con los datos provistos al momento de registrar al tipo de habitación.",
			tipoHabitacionX.equals(tipoHabitacion1));
	}
	
	@Test
	public void CadenaRegistraDosTiposHabitacion() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarTipoHabitacion(tipoHabitacion2);
		
		List<TipoHabitacionDTO> tiposHabitacion = asList(controller.getTiposHabitacion());
		
		assertTrue(
			"El sistema no devolvió los dos tipos de habitación registrados.",
			tiposHabitacion.size() == 2);
		
		TipoHabitacionDTO tipoHabitacionX = tiposHabitacion.get(0);
		
		TipoHabitacionDTO tipoHabitacionY = tiposHabitacion.get(1);
		
		assertTrue(
			"El sistema devolvió datos de tipos de habitación que no coinciden con los registrados",
			(tipoHabitacionX.equals(tipoHabitacion1) && tipoHabitacionY.equals(tipoHabitacion2)) ||
				(tipoHabitacionX.equals(tipoHabitacion2) && tipoHabitacionY.equals(tipoHabitacion1)));
	}
	
	@Test(expected = Exception.class)
	public void CadenaNoRegistraDosTiposHabitacionConElMismoNombre() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion(tipoHabitacion1.getNombre());
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarTipoHabitacion(tipoHabitacion2);
	}
	
	@Test
	public void CadenaNoPierdeInformacionDeTiposHabitacion() throws Exception
	{
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		ICadenaController controller1 = this.controllerFactory.createCadenaController();
		
		assertTrue(
			"El controlador no devolvió una lista de tipos de habitación vacía al inicio.",
			controller1.getTiposHabitacion().isEmpty());
		
		controller1.agregarTipoHabitacion(tipoHabitacion1.getNombre());
		
		List<TipoHabitacionDTO> tiposHabitacion1 = asList(controller1.getTiposHabitacion());
		
		assertTrue(
			"El sistema no registró al tipo de habitación.",
			!tiposHabitacion1.isEmpty());
		
		assertTrue(
			"El sistema registró más de un tipo de habitación.",
			tiposHabitacion1.size() == 1);
		
		assertTrue(
			"El sistema registró datos del tipo de habitación que no coinciden con los datos provistos al momento de registrar al tipo de habitación.",
			tiposHabitacion1.get(0).equals(tipoHabitacion1));
		
		ICadenaController controller2 = controllerFactory.createCadenaController();
		
		List<TipoHabitacionDTO> tiposHabitacion2 = asList(controller2.getTiposHabitacion());
		
		assertTrue(
			"El sistema no registró al tipo de habitación. Usando un segundo controlador, éste debe devolver el tipo de habitación registrado mediante el primer controlador.",
			!tiposHabitacion2.isEmpty());
		
		assertTrue(
			"El sistema registró más de un tipo de habitación. Usando un segundo controlador, éste debe devolver el único tipo de habitación registrado mediante el primer controlador.",
			tiposHabitacion2.size() == 1);
		
		assertTrue(
			"El sistema registró datos del tipo de habitación que no coinciden con los datos provistos. Los datos del tipo de habitación que devuelve el segundo controlador deben ser los mismos que los registrados mediante el primer controlador.",
			tiposHabitacion2.get(0).equals(tipoHabitacion1));
	}
}
