package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.system.IHacerReservaController;
import org.tds.sgh.test.TestBase;


@RunWith(JUnit4.class)
public class sugerirAlternativasTest extends TestBase
{
	@Test(expected = Exception.class)
	public void SistemaNoSugiereAlternativasDeTipoHabitacionInexistente() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2);
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoSugiereAlternativasDeFechaInicioEnElPasado() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFechaEnElPasado();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2);
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoSugiereAlternativasDeFechaInicioPosteriorAFechaFin() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha2, fecha1);
	}
	
	@Test
	public void NoSugiereAlternativasCuandoNoHayHoteles() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2));
		
		assertTrue(
			"No debe devolver hoteles alternativos ya que no hay hoteles registrados.",
			hoteles.isEmpty());
	}
	
	@Test
	public void NoSugiereAlternativasCuandoNoHayHabitacionesDelTipoHabitacion() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion2, fecha1, fecha2));
		
		assertTrue(
			"No debe devolver hoteles alternativos ya que no hay habitaciones del tipo de habitación indicado.",
			hoteles.isEmpty());
	}
	
	@Test
	public void SugiereAlternativasCuandoHayUnaHabitacionYNoHayReservas() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2));
		
		assertTrue(
			"Debe devolver un hotel.",
			hoteles.size() == 1);
		
		assertTrue(
			"Debe devolver el hotel1.",
			hotel1.equals(hoteles.get(0)));
	}
	
	@Test
	public void SugiereAlternativasCuandoHayDosHabitacionesYNoHayReservas() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2));
		
		assertTrue(
			"Debe devolver un hotel.",
			hoteles.size() == 1);
		
		assertTrue(
			"Debe devolver el hotel1.",
			hotel1.equals(hoteles.get(0)));
	}
	
	@Test
	public void SugiereAlternativasCuandoHayUnaHabitacionYHayReservasQueNoInterfieren() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		GregorianCalendar fecha4 = this.generarFecha(fecha3);
		GregorianCalendar fecha5 = this.generarFecha(fecha4);
		GregorianCalendar fecha6 = this.generarFecha(fecha5);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha3, fecha4, true);
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2));
		
		assertTrue(
			"Debe devolver el hotel1 ya que tiene una reserva pero no interfiere con las fechas.",
			hoteles.size() == 1 && hotel1.equals(hoteles.get(0)));
		
		hoteles = asList(hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha5, fecha6));
		
		assertTrue(
			"Debe devolver el hotel1 ya que tiene una reserva pero no interfiere con las fechas.",
			hoteles.size() == 1 && hotel1.equals(hoteles.get(0)));
	}
	
	@Test
	public void NoSugiereAlternativasCuandoHayUnaHabitacionYHayReservasQueInterfieren() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		GregorianCalendar fecha4 = this.generarFecha(fecha3);
		GregorianCalendar fecha5 = this.generarFecha(fecha4);
		GregorianCalendar fecha6 = this.generarFecha(fecha5);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController1 = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController1.seleccionarCliente(cliente1);
		
		hacerReservaController1.registrarReserva(hotel1, tipoHabitacion1, fecha2, fecha5, true);
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		List<HotelDTO> hoteles2 = asList(
			hacerReservaController2.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha3));
		
		assertTrue(
			"No debe devolver hoteles ya que hay un solo hotel pero tiene una reserva que interfiere.",
			hoteles2.isEmpty());
		
		hoteles2 = asList(
			hacerReservaController2.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha6));
		
		assertTrue(
			"No debe devolver hoteles ya que hay un solo hotel pero tiene una reserva que interfiere.",
			hoteles2.isEmpty());
		
		hoteles2 = asList(
			hacerReservaController2.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha3, fecha4));
		
		assertTrue(
			"No debe devolver hoteles ya que hay un solo hotel pero tiene una reserva que interfiere.",
			hoteles2.isEmpty());
		
		hoteles2 = asList(
			hacerReservaController2.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha4, fecha6));
		
		assertTrue(
			"No debe devolver hoteles ya que hay un solo hotel pero tiene una reserva que interfiere.",
			hoteles2.isEmpty());
	}
	
	@Test
	public void SugiereAlternativasDeDosHoteles() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotelEnPais(hotel1.getPais());
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2));
		
		assertTrue(
			"Debe devolver los dos hoteles.",
			hoteles.size() == 2);
		
		HotelDTO hotelX = hoteles.get(0);
		HotelDTO hotelY = hoteles.get(1);
		
		assertTrue(
			"Debe devolver el hotel1.",
			hotel1.equals(hotelX) || hotel1.equals(hotelY));
		
		assertTrue(
			"Debe devolver el hotel2.",
			hotel2.equals(hotelX) || hotel2.equals(hotelY));
	}
	
	@Test
	public void NoSugiereAlternativasDeHotelesDeDistintoPais() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha1, fecha2));
		
		assertTrue(
			"Debe devolver solo el hotel en el país.",
			hoteles.size() == 1);
		
		HotelDTO hotelX = hoteles.get(0);
		
		assertTrue(
			"Debe devolver el hotel1.",
			hotel1.equals(hotelX));
	}
}
