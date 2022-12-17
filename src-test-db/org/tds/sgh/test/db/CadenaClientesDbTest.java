package org.tds.sgh.test.db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.system.ICadenaController;


@RunWith(JUnit4.class)
public class CadenaClientesDbTest extends DbTestBase
{
	@Test
	public void ClientesNoEsNull()
	{
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		assertTrue(
			"El controlador devolvió null. Debe devolver una lista de clientes.",
			controller.getClientes() != null);
	}
	
	@Test
	public void ClientesEsVacio()
	{
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		assertTrue(
			"El controlador no devolvió una lista de clientes vacía al inicio.",
			controller.getClientes().isEmpty());
	}
	
	@Test
	public void CadenaRegistraCliente() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarCliente(cliente1);
		
		List<ClienteDTO> clientes = asList(controller.getClientes());
		
		assertTrue("El sistema no registró al cliente", !clientes.isEmpty());
		
		assertTrue("El sistema registró más de un cliente.", clientes.size() == 1);
		
		ClienteDTO cliente = clientes.get(0);
		
		assertTrue("El sistema devolvió null, debe devolver los datos del cliente registrado.", cliente != null);
		
		assertTrue(
			"El sistema reutilizó instancias de test; el sistema debe preservar su propia información.",
			cliente != cliente1);
		
		assertTrue(
			"El sistema registró datos del cliente que no coinciden con los datos provistos al momento de registrar al cliente.",
			cliente.equals(cliente1));
	}
	
	@Test
	public void CadenaRegistraDosClientes() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		ClienteDTO cliente2 = this.generarCliente();
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarCliente(cliente1);
		
		controller.agregarCliente(cliente2);
		
		List<ClienteDTO> clientes = asList(controller.getClientes());
		
		assertTrue(
			"El sistema no devolvió los dos clientes registrados.",
			clientes.size() == 2);
		
		ClienteDTO clienteX = clientes.get(0);
		
		ClienteDTO clienteY = clientes.get(1);
		
		assertTrue(
			"El sistema devolvió datos de clientes que no coinciden con los registrados",
			(clienteX.equals(cliente1) && clienteY.equals(cliente2)) ||
				(clienteX.equals(cliente2) && clienteY.equals(cliente1)));
	}
	
	@Test(expected = Exception.class)
	public void CadenaNoRegistraDosClientesConElMismoRut() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		ClienteDTO cliente2 = this.generarCliente(cliente1.getRut());
		
		ICadenaController controller = this.controllerFactory.createCadenaController();
		
		controller.agregarCliente(cliente1);
		
		controller.agregarCliente(cliente2);
	}
	
	@Test
	public void CadenaNoPierdeInformacionDeClientes() throws Exception
	{
		ClienteDTO cliente1 = this.generarCliente();
		
		ICadenaController controller1 = this.controllerFactory.createCadenaController();
		
		List<ClienteDTO> clientes1 = asList(controller1.getClientes());
		
		assertTrue(
			"El controlador no devolvió una lista de clientes vacía al inicio.",
			clientes1.isEmpty());
		
		controller1.agregarCliente(
			cliente1.getRut(),
			cliente1.getNombre(),
			cliente1.getDireccion(),
			cliente1.getTelefono(),
			cliente1.getMail());
		
		clientes1 = asList(controller1.getClientes());
		
		assertTrue(
			"El sistema no registró al cliente.",
			!clientes1.isEmpty());
		
		assertTrue(
			"El sistema registró más de un cliente.",
			clientes1.size() == 1);
		
		assertTrue(
			"El sistema registró datos del cliente que no coinciden con los datos provistos al momento de registrar al cliente.",
			clientes1.get(0).equals(cliente1));
		
		ICadenaController controller2 = controllerFactory.createCadenaController();
		
		List<ClienteDTO> clientes2 = asList(controller2.getClientes());
		
		assertTrue(
			"El sistema no registró al cliente. Usando un segundo controlador, éste debe devolver el cliente registrado mediante el primer controlador.",
			!clientes2.isEmpty());
		
		assertTrue(
			"El sistema registró más de un cliente. Usando un segundo controlador, éste debe devolver el único cliente registrado mediante el primer controlador.",
			clientes2.size() == 1);
		
		assertTrue(
			"El sistema registró datos del cliente que no coinciden con los datos provistos. Los datos del cliente que devuelve el segundo controlador deben ser los mismos que los registrados mediante el primer controlador.",
			clientes2.get(0).equals(cliente1));
	}
}
