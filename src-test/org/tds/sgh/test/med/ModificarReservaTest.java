package org.tds.sgh.test.med;

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
import org.tds.sgh.system.IModificarReservaController;
import org.tds.sgh.test.TestBase;
import org.tds.sgh.test.stubs.SistemaMensajeriaStub.Mail;


@RunWith(JUnit4.class)
public class ModificarReservaTest extends TestBase
{
	// --------------------------------------------------------------------------------------------
	// curso típico
	// --------------------------------------------------------------------------------------------
	
	@Test
	public void ModificarReservaCursoTipicoCambioDeFecha() throws Exception
	{
		ClienteDTO cliente1 = this.generarClienteConNombre("Fulanito");
		
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
		
		IModificarReservaController modificarReservaController = this.controllerFactory
			.createModificarReservaController();
		
		List<ClienteDTO> clientes2 = asList(modificarReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes2 != null && clientes2.size() == 1 && cliente1.equals(clientes2.get(0)));
		
		ClienteDTO clienteY = modificarReservaController.seleccionarCliente(clientes.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteY));
		
		List<ReservaDTO> reservas = asList(modificarReservaController.buscarReservasDelCliente());
		
		assertTrue(
			"Debe haber una única reserva del cliente.",
			reservas != null && reservas.size() == 1);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva.equals(reservas.get(0)));
		
		ReservaDTO reservaY = modificarReservaController.seleccionarReserva(reservas.get(0).getCodigo());
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva.equals(reservaY));
		
		disponibilidad = modificarReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha4);
		
		assertTrue(
			"Debe haber disponibilidad.",
			disponibilidad);
		
		ReservaDTO reservaZ = modificarReservaController
			.modificarReserva(hotel1, tipoHabitacion1, fecha3, fecha4, true);
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha3,
			fecha4,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva3.equals(reservaZ));
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe mandar dos mails al cliente confirmando la reserva (al registrar y al modificar).",
			mails.size() == 2);
		
		assertTrue(
			"El destinatario del mail debe ser el cliente de la reserva.",
			mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));
	}
	
	@Test
	public void ModificarReservaCursoTipicoCambioDeTipoHabitacion() throws Exception
	{
		ClienteDTO cliente1 = this.generarClienteConNombre("Fulanito");
		
		HotelDTO hotel1 = this.generarHotel();
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		TipoHabitacionDTO tipoHabitacion2 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel1, tipoHabitacion2);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		cadenaController.agregarTipoHabitacion(tipoHabitacion2);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
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
		
		IHacerReservaController hacerReservaController2 = controllerFactory.createHacerReservaController();
		
		List<ClienteDTO> clientes2 = asList(hacerReservaController2.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes2 != null && clientes2.size() == 1 && cliente1.equals(clientes2.get(0)));
		
		ClienteDTO clienteX2 = hacerReservaController2.seleccionarCliente(clientes2.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteX2));
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha3);
		
		assertTrue(
			"El hotel no debe tener disponibilidad ya que hay una reserva que interfiere.",
			!disponibilidad);
		
		IModificarReservaController modificarReservaController = controllerFactory.createModificarReservaController();
		
		List<ClienteDTO> clientes3 = asList(modificarReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes3 != null && clientes3.size() == 1 && cliente1.equals(clientes3.get(0)));
		
		ClienteDTO clienteY = modificarReservaController.seleccionarCliente(clientes3.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteY));
		
		List<ReservaDTO> reservas = asList(modificarReservaController.buscarReservasDelCliente());
		
		assertTrue(
			"Debe haber una única reserva del cliente.",
			reservas != null && reservas.size() == 1);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva.equals(reservas.get(0)));
		
		ReservaDTO reservaY = modificarReservaController.seleccionarReserva(reservas.get(0).getCodigo());
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva.equals(reservaY));
		
		disponibilidad = modificarReservaController.confirmarDisponibilidad(hotel1, tipoHabitacion2, fecha1, fecha2);
		
		assertTrue(
			"Debe haber disponibilidad ya que es otro tipo de habitación.",
			disponibilidad);
		
		ReservaDTO reservaZ = modificarReservaController
			.modificarReserva(hotel1, tipoHabitacion2, fecha1, fecha2, true);
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion2.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva3.equals(reservaZ));
		
		IHacerReservaController hacerReservaController4 = controllerFactory.createHacerReservaController();
		
		List<ClienteDTO> clientes4 = asList(hacerReservaController4.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes4 != null && clientes4.size() == 1 && cliente1.equals(clientes4.get(0)));
		
		ClienteDTO clienteX4 = hacerReservaController4.seleccionarCliente(clientes4.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteX4));
		
		disponibilidad = hacerReservaController4.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha3);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que la reserva que interfería fue modificada.",
			disponibilidad);
		
		ReservaDTO reservaX4 = hacerReservaController4.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha3, true);
		
		ReservaDTO reserva4 = new ReservaDTO(
			reservaX4.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha3,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva4.equals(reservaX4));
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe mandar dos mails al cliente confirmando la reserva (registrar, modificar, registrar).",
			mails.size() == 3);
		
		assertTrue(
			"El destinatario del mail debe ser el cliente de la reserva.",
			mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));
	}
	
	@Test
	public void ModificarReservaCursoTipicoCambioDeHotel() throws Exception
	{
		ClienteDTO cliente1 = this.generarClienteConNombre("Fulanito");
		
		HotelDTO hotel1 = this.generarHotel();
		HotelDTO hotel2 = this.generarHotel(hotel1.getPais());
		
		TipoHabitacionDTO tipoHabitacion1 = this.generarTipoHabitacion();
		
		HabitacionDTO habitacion1 = this.generarHabitacion(hotel1, tipoHabitacion1);
		HabitacionDTO habitacion2 = this.generarHabitacion(hotel2, tipoHabitacion1);
		
		GregorianCalendar fecha1 = this.generarFecha();
		GregorianCalendar fecha2 = this.generarFecha(fecha1);
		GregorianCalendar fecha3 = this.generarFecha(fecha2);
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		cadenaController.agregarHotel(hotel1);
		cadenaController.agregarHotel(hotel2);
		
		cadenaController.agregarTipoHabitacion(tipoHabitacion1);
		
		cadenaController.agregarHabitacion(habitacion1);
		cadenaController.agregarHabitacion(habitacion2);
		
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
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		List<ClienteDTO> clientes2 = asList(hacerReservaController2.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes2 != null && clientes2.size() == 1 && cliente1.equals(clientes2.get(0)));
		
		ClienteDTO clienteX2 = hacerReservaController2.seleccionarCliente(clientes2.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteX2));
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha3);
		
		assertTrue(
			"El hotel no debe tener disponibilidad ya que hay una reserva que interfiere.",
			!disponibilidad);
		
		IModificarReservaController modificarReservaController = this.controllerFactory
			.createModificarReservaController();
		
		List<ClienteDTO> clientes3 = asList(modificarReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes3 != null && clientes3.size() == 1 && cliente1.equals(clientes3.get(0)));
		
		ClienteDTO clienteY = modificarReservaController.seleccionarCliente(clientes3.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteY));
		
		List<ReservaDTO> reservas = asList(modificarReservaController.buscarReservasDelCliente());
		
		assertTrue(
			"Debe haber una única reserva del cliente.",
			reservas != null && reservas.size() == 1);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva.equals(reservas.get(0)));
		
		ReservaDTO reservaY = modificarReservaController.seleccionarReserva(reservas.get(0).getCodigo());
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva.equals(reservaY));
		
		disponibilidad = modificarReservaController.confirmarDisponibilidad(hotel2, tipoHabitacion1, fecha1, fecha2);
		
		assertTrue(
			"Debe haber disponibilidad ya que es otro hotel.",
			disponibilidad);
		
		ReservaDTO reservaZ = modificarReservaController
			.modificarReserva(hotel2, tipoHabitacion1, fecha1, fecha2, true);
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel2.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva3.equals(reservaZ));
		
		IHacerReservaController hacerReservaController4 = this.controllerFactory.createHacerReservaController();
		
		List<ClienteDTO> clientes4 = asList(hacerReservaController4.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes4 != null && clientes4.size() == 1 && cliente1.equals(clientes4.get(0)));
		
		ClienteDTO clienteX4 = hacerReservaController4.seleccionarCliente(clientes4.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteX4));
		
		disponibilidad = hacerReservaController4.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha3);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que la reserva que interfería fue modificada.",
			disponibilidad);
		
		ReservaDTO reservaX4 = hacerReservaController4.registrarReserva(hotel1, tipoHabitacion1, fecha1, fecha3, true);
		
		ReservaDTO reserva4 = new ReservaDTO(
			reservaX4.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha3,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva4.equals(reservaX4));
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe mandar dos mails al cliente confirmando la reserva (registrar, modificar, registrar).",
			mails.size() == 3);
		
		assertTrue(
			"El destinatario del mail debe ser el cliente de la reserva.",
			mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));
	}
	
	// --------------------------------------------------------------------------------------------
	// curso alternativo
	// --------------------------------------------------------------------------------------------
	
	@Test
	public void ModificarReservaCursoAlternativoSinDisponibilidad() throws Exception
	{
		ClienteDTO cliente1 = this.generarClienteConNombre("Fulanito");
		
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
		
		IHacerReservaController hacerReservaController2 = this.controllerFactory.createHacerReservaController();
		
		List<ClienteDTO> clientes2 = asList(hacerReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes2 != null && clientes2.size() == 1 && cliente1.equals(clientes2.get(0)));
		
		ClienteDTO clienteX2 = hacerReservaController2.seleccionarCliente(clientes2.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteX2));
		
		disponibilidad = hacerReservaController2.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha4);
		
		assertTrue(
			"El hotel debe tener disponibilidad ya que no tiene ninguna reserva que interfiera.",
			disponibilidad);
		
		ReservaDTO reservaX2 = hacerReservaController2.registrarReserva(hotel1, tipoHabitacion1, fecha3, fecha4, true);
		
		ReservaDTO reserva2 = new ReservaDTO(
			reservaX2.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha3,
			fecha4,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva2.equals(reservaX2));
		
		IModificarReservaController modificarReservaController3 = controllerFactory.createModificarReservaController();
		
		List<ClienteDTO> clientes3 = asList(modificarReservaController3.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes3 != null && clientes3.size() == 1 && cliente1.equals(clientes3.get(0)));
		
		ClienteDTO clienteY = modificarReservaController3.seleccionarCliente(clientes3.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteY));
		
		List<ReservaDTO> reservas3 = asList(modificarReservaController3.buscarReservasDelCliente());
		
		assertTrue(
			"Debe haber dos reservas del cliente.",
			reservas3 != null && reservas3.size() == 2);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva.equals(reservas3.stream().filter(r -> r.getCodigo() == reserva.getCodigo()).findFirst().get()));
		
		ReservaDTO reservaY = modificarReservaController3.seleccionarReserva(reserva.getCodigo());
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva.equals(reservaY));
		
		disponibilidad = modificarReservaController3.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha4);
		
		assertTrue(
			"No debe haber disponibilidad ya que interfiere con la reserva registrada.",
			!disponibilidad);
		
		disponibilidad = modificarReservaController3.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha2, fecha4);
		
		assertTrue(
			"No debe haber disponibilidad ya que interfiere con la reserva registrada.",
			!disponibilidad);
		
		disponibilidad = modificarReservaController3.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha4);
		
		assertTrue(
			"No debe haber disponibilidad ya que interfiere con la reserva registrada.",
			!disponibilidad);
		
		disponibilidad = modificarReservaController3.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha1, fecha5);
		
		assertTrue(
			"No debe haber disponibilidad ya que interfiere con la reserva registrada.",
			!disponibilidad);
		
		disponibilidad = modificarReservaController3.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha2, fecha6);
		
		assertTrue(
			"No debe haber disponibilidad ya que interfiere con la reserva registrada.",
			!disponibilidad);
		
		disponibilidad = modificarReservaController3.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha3, fecha5);
		
		assertTrue(
			"No debe haber disponibilidad ya que interfiere con la reserva registrada.",
			!disponibilidad);
		
		disponibilidad = modificarReservaController3.confirmarDisponibilidad(hotel1, tipoHabitacion1, fecha5, fecha6);
		
		assertTrue(
			"Debe haber disponibilidad.",
			disponibilidad);
		
		ReservaDTO reservaZ = modificarReservaController3
			.modificarReserva(hotel1, tipoHabitacion1, fecha5, fecha6, true);
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha5,
			fecha6,
			true,
			"Pendiente",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva3.equals(reservaZ));
		
		List<Mail> mails = this.sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe mandar tres mails al cliente confirmando la reserva (registrar, registrar y modificar).",
			mails.size() == 3);
		
		assertTrue(
			"El destinatario del mail debe ser el cliente de la reserva.",
			mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));
	}
}
