package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.system.ICancelarReservaController;
import org.tds.sgh.system.IHacerReservaController;
import org.tds.sgh.system.IIdentificarReservaClienteController;
import org.tds.sgh.system.ITomarReservaController;
import org.tds.sgh.test.TestBase;


public class CancelarReservaTest extends TestBase
{
	@Test
	public void BuscaReservasValidasDeClienteSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fechaHoy1 = this.calendario.getHoy();
		
		GregorianCalendar fecha1 = this.generarFecha(fechaHoy1);
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		GregorianCalendar fechaHoy2 = this.generarFecha(fecha2);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		this.cambiarHoyEnCalendario(fechaHoy1);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		ReservaDTO reservaRegistrada = hacerReservaController
			.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		ICancelarReservaController cancelarReservaController = this.controllerFactory.createCancelarReservaController();
		
		cancelarReservaController.seleccionarCliente(cliente1);
		
		List<ReservaDTO> reservas = asList(cancelarReservaController.buscarReservasDelCliente());
		
		assertTrue(
			"Debe devolver la reserva registrada.",
			reservas != null && reservas.size() == 1);
		
		cancelarReservaController.seleccionarReserva(reservas.get(0));
		
		ReservaDTO reservaCancelada = cancelarReservaController.cancelarReservaDelCliente();
		
		assertTrue(
			"La reserva cancelada debe ser la misma que la registrada.",
			reservaRegistrada.getCodigo() == reservaCancelada.getCodigo());
		
		this.cambiarHoyEnCalendario(fechaHoy2);
		
		IIdentificarReservaClienteController modificarReservaController = this.controllerFactory
			.createModificarReservaController();
		
		modificarReservaController.seleccionarCliente(cliente1);
		
		reservas = asList(modificarReservaController.buscarReservasDelCliente());
		
		assertTrue(
			"Debe devolver una lista vacía de reservas ya que no hay reservas válidas para el cliente.",
			reservas != null && reservas.isEmpty());
	}
	
	@Test
	public void ReservaCanceladaNoEstaPendienteEnHotel() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		
		IHacerReservaController hacerReservaController1 = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController1.seleccionarCliente(cliente1);
		
		ReservaDTO reservaRegistrada = hacerReservaController1
			.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		ICancelarReservaController cancelarReservaController2 = this.controllerFactory
			.createCancelarReservaController();
		
		cancelarReservaController2.seleccionarCliente(cliente1);
		
		List<ReservaDTO> reservas2 = asList(cancelarReservaController2.buscarReservasDelCliente());
		
		assertTrue(
			"Debe devolver la reserva registrada.",
			reservas2 != null && reservas2.size() == 1);
		
		cancelarReservaController2.seleccionarReserva(reservas2.get(0));
		
		ReservaDTO reservaCancelada = cancelarReservaController2.cancelarReservaDelCliente();
		
		assertTrue(
			"La reserva cancelada debe ser la misma que la registrada.",
			reservaRegistrada.getCodigo() == reservaCancelada.getCodigo());
		
		this.cambiarHoyEnCalendario(fecha1);
		
		ITomarReservaController tomarReservaController3 = this.controllerFactory.createTomarReservaController();
		
		List<ReservaDTO> reservas3 = asList(tomarReservaController3.buscarReservasPendientes(hotel1));
		
		assertTrue(
			"Debe devolver una lista vacía de reservas ya que la reserva que está registrada está cancelada.",
			reservas3 != null && reservas3.isEmpty());
	}
}
