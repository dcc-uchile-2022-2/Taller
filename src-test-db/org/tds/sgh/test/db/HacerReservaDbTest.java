package org.tds.sgh.test.db;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

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
import org.tds.sgh.test.stubs.SistemaMensajeriaStub.Mail;


@RunWith(JUnit4.class)
public class HacerReservaDbTest extends DbTestBase
{
	// --------------------------------------------------------------------------------------------
	// curso típico
	// --------------------------------------------------------------------------------------------
	
	@Test
	public void HacerReservaCursoTipico() throws Exception
	{
		ClienteDTO cliente1 = this.generarClienteConNombre("Fulanito");
		
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
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<ClienteDTO> clientes = asList(hacerReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes != null && clientes.size() == 1 && cliente1.equals(clientes.get(0)));
		
		ClienteDTO clienteX = hacerReservaController.seleccionarCliente(clientes.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteX));
		
		boolean disponibilidad = hacerReservaController
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no tiene ninguna reserva que interfiera.",
			disponibilidad);
		
		ReservaDTO reservaX = hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		ReservaDTO reserva = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva.equals(reservaX));
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe mandar un mail al cliente confirmando la reserva.",
			mails.size() == 1);
		
		assertTrue(
			"El destinatario del mail debe ser el cliente de la reserva.",
			mails.get(0).getDestinatario().equals(cliente1.getMail()));
	}
	
	// --------------------------------------------------------------------------------------------
	// curso alternativo con alta de cliente
	// --------------------------------------------------------------------------------------------
	
	@Test
	public void HacerReservaCursoAlternativoConAltaDeCliente() throws Exception
	{
		ClienteDTO cliente1 = this.generarClienteConNombre("Fulanito");
		
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
		
		List<ClienteDTO> clientes = asList(hacerReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El sistema no debe devolver clientes ya que no hay clientes registrados.",
			clientes != null && clientes.size() == 0);
		
		ClienteDTO clienteX = hacerReservaController.registrarCliente(cliente1);
		
		assertTrue(
			"El cliente registrado debe ser el cliente1.",
			cliente1.equals(clienteX));
		
		boolean disponibilidad = hacerReservaController
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no tiene ninguna reserva que interfiera.",
			disponibilidad);
		
		ReservaDTO reservaX = hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha2, true);
		
		ReservaDTO reserva = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva.equals(reservaX));
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe mandar un mail al cliente confirmando la reserva.",
			mails.size() == 1);
		
		assertTrue(
			"El destinatario del mail debe ser el cliente de la reserva.",
			mails.get(0).getDestinatario().equals(cliente1.getMail()));
	}
	
	// --------------------------------------------------------------------------------------------
	// curso alternativo sin disponibilidad
	// --------------------------------------------------------------------------------------------
	
	@Test
	public void HacerReservaCursoAlternativoSinDisponibilidad() throws Exception
	{
		ClienteDTO cliente1 = this.generarClienteConNombre("Fulanito");
		
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotel();
		HotelDTO hotel3 = this.generarHotelEnPais(hotel1.getPais());
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion11 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion12 = this.generarHabitacion(hotel1, tipoHabitacion2);
		HabitacionDTO habitacion13 = this.generarHabitacion(hotel1, tipoHabitacion2);
		HabitacionDTO habitacion21 = this.generarHabitacion(hotel2, tipoHabitacion1);
		HabitacionDTO habitacion31 = this.generarHabitacion(hotel3, tipoHabitacion1);
		HabitacionDTO habitacion32 = this.generarHabitacion(hotel3, tipoHabitacion2);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		GregorianCalendar fecha4 = this.generarFecha(fecha3);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		cadenaController.agregarHotel(hotel3);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion11);
		cadenaController.agregarHabitacion(habitacion12);
		cadenaController.agregarHabitacion(habitacion13);
		cadenaController.agregarHabitacion(habitacion21);
		cadenaController.agregarHabitacion(habitacion31);
		cadenaController.agregarHabitacion(habitacion32);
		
		IHacerReservaController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		List<ClienteDTO> clientes = asList(hacerReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes != null && clientes.size() == 1 && cliente1.equals(clientes.get(0)));
		
		ClienteDTO clienteX = hacerReservaController.seleccionarCliente(cliente1);
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteX));
		
		boolean disponibilidad = hacerReservaController
			.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha3);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no tiene ninguna reserva que interfiera.",
			disponibilidad);
		
		ReservaDTO reservaX = hacerReservaController.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha3, true);
		
		ReservaDTO reserva1 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha3,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no son los esperados.",
			reserva1.equals(reservaX));
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		clientes = asList(hacerReservaController2.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes != null && clientes.size() == 1 && cliente1.equals(clientes.get(0)));
		
		ClienteDTO clienteY = hacerReservaController2.seleccionarCliente(cliente1);
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteY));
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha2, fecha4);
		
		assertTrue(
			"El hotel no debe tener disponibilidad ya hay una sola habitación del tipo indicado y hay una reserva que interfiere con las fechas indicadas.",
			!disponibilidad);
		
		List<HotelDTO> hoteles = asList(
			hacerReservaController2
				.sugerirAlternativas(hotel1.getPais(), tipoHabitacion1, fecha2, fecha4));
		
		assertTrue(
			"El hotel3 es el único que debe ser sugerido como alternativa ya que tiene disponibilidad y está en el mismo país.",
			hoteles != null && hoteles.size() == 1 && hotel3.equals(hoteles.get(0)));
		
		ReservaDTO reservaY = hacerReservaController2.registrarReserva(hotel3, tipoHabitacion1, fecha2, fecha4, true);
		
		ReservaDTO reserva2 = new ReservaDTO(
			reservaY.getCodigo(),
			cliente1.getRut(),
			hotel3.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha2,
			fecha4,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva2.equals(reservaY));
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe haber mandado dos mails al cliente ya que se han hecho dos reservas en total.",
			mails.size() == 2);
		
		assertTrue(
			"El destinatario de los mails debe ser el cliente1.",
			mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));
	}
}
