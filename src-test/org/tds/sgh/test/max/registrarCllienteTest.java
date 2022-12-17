package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.system.IAltaClienteController;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.test.TestBase;


@RunWith(JUnit4.class)
public class registrarCllienteTest extends TestBase
{
	@Test
	public void RegistraCliente() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		IAltaClienteController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		ClienteDTO clienteX = hacerReservaController.registrarCliente(cliente1);
		
		assertTrue(
			"Los datos del cliente registrado no coinciden con los datos provistos al momento de registrar al cliente.",
			cliente1.equals(clienteX));
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		List<ClienteDTO> clientes = asList(cadenaController.getClientes());
		
		assertTrue(
			"El cliente no quedó registrado.",
			clientes.size() == 1);
		
		assertTrue(
			"Los datos del cliente registrado no coincide con los datos provistos al momento de registrar al cliente.",
			cliente1.equals(clientes.get(0)));
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoRegistraClienteConRutDeClienteExistente() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		ClienteDTO cliente2 = this.generarCliente(cliente1.getRut());
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		
		IAltaClienteController hacerReservaController = this.controllerFactory.createHacerReservaController();
		
		hacerReservaController.registrarCliente(cliente2);
	}
}
