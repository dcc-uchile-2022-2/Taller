package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

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
public class confirmarDisponibilidadTest extends TestBase
{
	@Test(expected = Exception.class)
	public void SistemaNoConfirmaDisponibilidadDeHotelInexistente() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fechaInicio = this.generarFecha();
		GregorianCalendar fechaFin = this.generarFecha(fechaInicio);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fechaInicio, fechaFin);
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoConfirmaDisponibilidadDeTipoHabitacionInexistente() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoConfirmaDisponibilidadDeFechaInicioEnElPasado() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFechaEnElPasado();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoConfirmaDisponibilidadDeFechaInicioPosteriorAFechaFin() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha2, fecha1);
	}
	
	@Test
	public void NoHayDisponibilidadParaHotelSinHabitacionesDelTipoHabitacion() throws Exception
	{
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		boolean disponibilidad = hacerReservaController
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"El hotel no puede tener disponibilidad ya que no tiene habitaciones del tipo indicado.",
			!disponibilidad);
	}
	
	@Test
	public void NoHayDisponibilidadParaHotelConHabitacionesPeroNoDelTipoHabitacion() throws Exception
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
		
		boolean disponibilidad = hacerReservaController
			.confirmarDisponibilidad(hotel1, tipoHabitacion2, fecha1, fecha2);
		
		assertTrue(
			"El hotel no puede tener disponibilidad ya que no tiene habitaciones del tipo indicado.",
			!disponibilidad);
	}
	
	@Test
	public void HayDisponibilidadParaHotelConHabitacionesDelTipoHabitacionYSinReservas() throws Exception
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
		
		boolean disponibilidad = hacerReservaController
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que tiene una habitación del tipo iniciado y no hay reservas registradas.",
			disponibilidad);
	}
	
	@Test
	public void HayDisponibilidadParaHotelConReservasPrevias() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		GregorianCalendar fecha4 = this.generarFecha(fecha3);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController1 = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController1.seleccionarCliente(cliente1);
		
		boolean disponibilidad = hacerReservaController1
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no hay reservas registradas.",
			disponibilidad);
		
		hacerReservaController1.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha4);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que la reserva registrada es previa a las fechas indicadas.",
			disponibilidad);
	}
	
	@Test
	public void HayDisponibilidadParaHotelConReservasPosterioes() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		GregorianCalendar fecha4 = this.generarFecha(fecha3);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController1 = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController1.seleccionarCliente(cliente1);
		
		boolean disponibilidad = hacerReservaController1
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha4);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no hay reservas registradas.",
			disponibilidad);
		
		hacerReservaController1.registrarReserva(hotel1, tipoHabitacion1, fecha3, fecha4, false);
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que la reserva registrada es posterior a las fechas indicadas.",
			disponibilidad);
	}
	
	@Test
	public void HayDisponibilidadParaHotelConReservasConsecutivas() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController1 = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController1.seleccionarCliente(cliente1);
		
		boolean disponibilidad = hacerReservaController1
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no hay reservas registradas.",
			disponibilidad);
		
		hacerReservaController1.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha2, fecha3);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que la reserva registrada termina (check-out) en la fecha de check-in indicado.",
			disponibilidad);
	}
	
	@Test
	public void NoHayDisponibilidadParaHotelConReservasQueInterfiere() throws Exception
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
		
		boolean disponibilidad = hacerReservaController1
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha2, fecha5);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no hay reservas registradas.",
			disponibilidad);
		
		hacerReservaController1.registrarReserva(hotel1, tipoHabitacion1, fecha2, fecha5, true);
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha3);
		
		assertTrue(
			"El hotel no debe tener disponibilidad ya que la reserva registrada interfiere.",
			!disponibilidad);
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha6);
		
		assertTrue(
			"El hotel no debe tener disponibilidad ya que la reserva registrada interfiere.",
			!disponibilidad);
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha4);
		
		assertTrue(
			"El hotel no debe tener disponibilidad ya que la reserva registrada interfiere.",
			!disponibilidad);
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha6);
		
		assertTrue(
			"El hotel no debe tener disponibilidad ya que la reserva registrada interfiere.",
			!disponibilidad);
	}
}
