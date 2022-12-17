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
import org.tds.sgh.system.ICancelarReservaController;
import org.tds.sgh.system.IHacerReservaController;
import org.tds.sgh.test.TestBase;
import org.tds.sgh.test.stubs.SistemaMensajeriaStub.Mail;


@RunWith(JUnit4.class)
public class CancelarReservaTest extends TestBase
{
	// --------------------------------------------------------------------------------------------
	// curso típico
	// --------------------------------------------------------------------------------------------
	
	@Test
	public void CancelarReservaCursoTipico() throws Exception
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
		
		ICancelarReservaController cancelarReservaController = this.controllerFactory.createCancelarReservaController();
		
		List<ClienteDTO> clientes2 = asList(cancelarReservaController.buscarCliente(".*lani.*"));
		
		assertTrue(
			"El cliente devuelto debe ser el cliente1.",
			clientes2 != null && clientes2.size() == 1 && cliente1.equals(clientes2.get(0)));
		
		ClienteDTO clienteY = cancelarReservaController.seleccionarCliente(clientes.get(0));
		
		assertTrue(
			"El cliente seleccionado debe ser el cliente1.",
			cliente1.equals(clienteY));
		
		List<ReservaDTO> reservas = asList(cancelarReservaController.buscarReservasDelCliente());
		
		assertTrue(
			"Debe haber una única reserva del cliente.",
			reservas != null && reservas.size() == 1);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los provistos al momento de registrar la reserva.",
			reserva.equals(reservas.get(0)));
		
		ReservaDTO reservaY = cancelarReservaController.seleccionarReserva(reservas.get(0).getCodigo());
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva.equals(reservaY));
		
		ReservaDTO reservaZ = cancelarReservaController.cancelarReservaDelCliente();
		
		ReservaDTO reserva3 = new ReservaDTO(
			reservaX.getCodigo(),
			cliente1.getRut(),
			hotel1.getNombre(),
			tipoHabitacion1.getNombre(),
			fecha1,
			fecha2,
			true,
			"Cancelada",
			null);
		
		assertTrue(
			"Los datos de la reserva no coinciden con los esperados.",
			reserva3.equals(reservaZ));
		
		List<Mail> mails = sistemaMensajeria.getMailEnviados();
		
		assertTrue(
			"El sistema debe mandar dos mails mail al cliente confirmando la reserva (al registrar y al cancelar).",
			mails.size() == 2);
		
		assertTrue(
			"El destinatario del mail debe ser el cliente de la reserva.",
			mails.stream().allMatch(mail -> mail.getDestinatario().equals(cliente1.getMail())));
	}
}
