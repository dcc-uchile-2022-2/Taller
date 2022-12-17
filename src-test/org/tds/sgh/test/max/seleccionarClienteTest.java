package org.tds.sgh.test.max;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.system.ICadenaController;
import org.tds.sgh.system.IIdentificarClienteEnRecepcionController;
import org.tds.sgh.test.TestBase;


@RunWith(JUnit4.class)
public class seleccionarClienteTest extends TestBase
{
	@Test(expected = Exception.class)
	public void SistemaNoSeleccionaClienteCuandoNoHayClientesRegistrados() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		IIdentificarClienteEnRecepcionController hacerReservaController = this.controllerFactory
			.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente1);
	}
	
	@Test(expected = Exception.class)
	public void SistemaNoSeleccionaClienteCuandoNoEstaRegistrado() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		ClienteDTO cliente2 = this.generarCliente();
		ClienteDTO cliente3 = this.generarCliente();
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		cadenaController.agregarCliente(cliente2);
		
		IIdentificarClienteEnRecepcionController hacerReservaController = this.controllerFactory
			.createHacerReservaController();
		
		hacerReservaController.seleccionarCliente(cliente3);
	}
	
	@Test
	public void SistemaSeleccionaClienteCuandoClienteEstaRegistrado() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		ClienteDTO cliente2 = this.generarCliente();
		ClienteDTO cliente3 = this.generarCliente();
		
		ICadenaController cadenaController = this.controllerFactory.createCadenaController();
		
		cadenaController.agregarCliente(cliente1);
		cadenaController.agregarCliente(cliente2);
		cadenaController.agregarCliente(cliente3);
		
		IIdentificarClienteEnRecepcionController hacerReservaController = this.controllerFactory
			.createHacerReservaController();
		
		ClienteDTO clienteX = hacerReservaController.seleccionarCliente(cliente2);
		
		assertTrue(
			"Los datos del cliente seleccionado no coinciden con los datos del cliente.",
			cliente2.equals(clienteX));
	}
}
