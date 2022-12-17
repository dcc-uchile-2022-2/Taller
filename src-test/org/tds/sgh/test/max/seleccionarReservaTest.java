package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.system.IHacerReservaController;
import org.tds.sgh.system.IIdentificarReservaClienteController;
import org.tds.sgh.test.TestBase;


@RunWith(JUnit4.class)
public class seleccionarReservaTest extends TestBase
{
	@Test(expected = Exception.class)
	public void SistemaNoSeleccionaReservaDeClienteNoSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion2);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		ReservaDTO reservaRegistrada = hacerReservaController
			.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		IIdentificarReservaClienteController modificarReservaController = this.controllerFactory
			.createModificarReservaController();
		
		modificarReservaController.seleccionarReserva(reservaRegistrada.getCodigo());
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoSeleccionaReservaDeClienteDistintoAlSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		ClienteDTO cliente2 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion2);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		cadenaController.agregarCliente(cliente2);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		ReservaDTO reservaRegistrada = hacerReservaController
			.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		IIdentificarReservaClienteController modificarReservaController = this.controllerFactory
			.createModificarReservaController();
		
		modificarReservaController.seleccionarCliente(cliente2);
		
		modificarReservaController.seleccionarReserva(reservaRegistrada.getCodigo());
	}
	
	@Test
	public void SeleccionaReservaDeClienteSeleccionado() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion2);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		ReservaDTO reservaRegistrada = hacerReservaController
			.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		IIdentificarReservaClienteController modificarReservaController = this.controllerFactory
			.createModificarReservaController();
		
		modificarReservaController.seleccionarCliente(cliente1);
		
		ReservaDTO reservaSeleccionada = modificarReservaController.seleccionarReserva(reservaRegistrada.getCodigo());
		
		assertTrue(
			"Los datos de la reserva seleccionada no coinciden con los datos de la reserva registrada.",
			reservaSeleccionada.equals(reservaRegistrada));
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoSeleccionaReservaDeCodigoInexistente() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion2);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
		
		hacerReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		IIdentificarReservaClienteController modificarReservaController = this.controllerFactory
			.createModificarReservaController();
		
		modificarReservaController.seleccionarReserva(-1);
	}
}
