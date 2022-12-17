package org.tds.sgh.test.min;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.test.TestBase;


@RunWith(JUnit4.class)
public class CadenaHabitacionesTest extends TestBase
{
	@Test
	public void HabitacionesDeHotelNoEsNull() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		assertTrue(
			"El controlador devolvió null. Debe devolver una lista de habitaciones.",
			controller.getHabitaciones(hotel1) != null);
	}
	
	@Test
	public void HabitacionesDeHotelEsVaciaAlInicio() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		assertTrue(
			"El controlador no devolvió una lista de habitaciones vacía al inicio.",
			controller.getHabitaciones(hotel1).isEmpty());
	}
	
	@Test(expected = Exception.class)
	public void CadenaNoEntregaHabitacionesDeHotelInexistente() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.getHabitaciones(hotel1);
	}
	
	@Test
	public void CadenaRegistraHabitacion() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarHabitacion(habitacion1);
		
		List<HabitacionDTO> habitaciones = asList(controller.getHabitaciones(hotel1));
		
		assertTrue(
			"El sistema devolvió null. Debe devolver la lista de habitaciones del hotel.",
			habitaciones != null);
		
		assertTrue(
			"El sistema devolvió una lista con más de una habitación y solo hay una habitación registrada.",
			habitaciones.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones.get(0);
		
		assertTrue(
			"El sistema devolvió null. Debe devolver los datos de la habitación registrada.",
			habitacionX != null);
		
		assertTrue(
			"El sistema registró datos de la habitación que no coinciden con los datos provistos al momento de registrar la habiración.",
			habitacionX.equals(habitacion1));
	}
	
	@Test(expected = Exception.class)
	public void CadenaNoRegistraHabitacionDeHotelInexistente() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarHabitacion(habitacion1);
	}
	
	@Test(expected = Exception.class)
	public void CadenaNoRegistraHabitacionDeTipoHabitacionInexistente() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarHabitacion(habitacion1);
	}
	
	@Test(expected = Exception.class)
	public void CadenaNoRegistraDosHabitacionesIgualHotelIgualTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel1, tipoHabitacion1, habitacion1.getNombre());
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesIgualHotelIgualTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
		
		List<HabitacionDTO> habitaciones = asList(controller.getHabitaciones(hotel1));
		
		assertTrue(
			"El sistema no registró las dos habitaciones del hotel.",
			habitaciones.size() == 2);
		
		HabitacionDTO habitacionX = habitaciones.get(0);
		
		HabitacionDTO habitacionY = habitaciones.get(1);
		
		assertTrue(
			"El sistema devolvió datos de habitaciones que no coinciden con las registradas.",
			(habitacionX.equals(habitacion1) && habitacionY.equals(habitacion2)) ||
				(habitacionX.equals(habitacion2) && habitacionY.equals(habitacion1)));
	}
	
	@Test(expected = Exception.class)
	public void CadenaNoRegistraDosHabitacionesIgualHotelDistintoTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel1, tipoHabitacion2, habitacion1.getNombre());
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarTipoHabitacion(tipoHabitacion2);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesIgualHotelDistintoTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel1, tipoHabitacion2);
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarTipoHabitacion(tipoHabitacion2);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
		
		List<HabitacionDTO> habitaciones = asList(controller.getHabitaciones(hotel1));
		
		assertTrue(
			"El sistema no registró las dos habitaciones del hotel.",
			habitaciones.size() == 2);
		
		HabitacionDTO habitacionX = habitaciones.get(0);
		
		HabitacionDTO habitacionY = habitaciones.get(1);
		
		assertTrue(
			"El sistema devolvió datos de habitaciones que no coinciden con las registradas.",
			(habitacionX.equals(habitacion1) && habitacionY.equals(habitacion2)) ||
				(habitacionX.equals(habitacion2) && habitacionY.equals(habitacion1)));
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelIgualTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion1, habitacion1.getNombre());
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarHotel(hotel2);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
		
		List<HabitacionDTO> habitaciones1 = asList(controller.getHabitaciones(hotel1));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionX.equals(habitacion1));
		
		List<HabitacionDTO> habitaciones2 = asList(controller.getHabitaciones(hotel2));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionY.equals(habitacion2));
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelIgualTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion1);
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarHotel(hotel2);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
		
		List<HabitacionDTO> habitaciones1 = asList(controller.getHabitaciones(hotel1));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionX.equals(habitacion1));
		
		List<HabitacionDTO> habitaciones2 = asList(controller.getHabitaciones(hotel2));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionY.equals(habitacion2));
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelDistintoTipoIgualNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion2, habitacion1.getNombre());
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarHotel(hotel2);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarTipoHabitacion(tipoHabitacion2);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
		
		List<HabitacionDTO> habitaciones1 = asList(controller.getHabitaciones(hotel1));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionX.equals(habitacion1));
		
		List<HabitacionDTO> habitaciones2 = asList(controller.getHabitaciones(hotel2));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionY.equals(habitacion2));
	}
	
	@Test
	public void CadenaRegistraDosHabitacionesDistintoHotelDistintoTipoDistintoNombre() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion2);
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarHotel(hotel1);
		
		controller.agregarHotel(hotel2);
		
		controller.agregarTipoHabitacion(tipoHabitacion1);
		
		controller.agregarTipoHabitacion(tipoHabitacion2);
		
		controller.agregarHabitacion(habitacion1);
		
		controller.agregarHabitacion(habitacion2);
		
		List<HabitacionDTO> habitaciones1 = asList(controller.getHabitaciones(hotel1));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones1.size() == 1);
		
		HabitacionDTO habitacionX = habitaciones1.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionX.equals(habitacion1));
		
		List<HabitacionDTO> habitaciones2 = asList(controller.getHabitaciones(hotel2));
		
		assertTrue(
			"El sistema no registró la habitación de uno de los hoteles.",
			habitaciones2.size() == 1);
		
		HabitacionDTO habitacionY = habitaciones2.get(0);
		
		assertTrue(
			"El sistema no registró una de las habitaciones.",
			habitacionY.equals(habitacion2));
	}
}
