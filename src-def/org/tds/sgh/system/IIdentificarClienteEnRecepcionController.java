package org.tds.sgh.system;

import java.util.Set;

import org.tds.sgh.dtos.ClienteDTO;


public interface IIdentificarClienteEnRecepcionController
{
	// --------------------------------------------------------------------------------------------
	
	Set<ClienteDTO> buscarCliente(String patronNombreCliente);
	
	ClienteDTO seleccionarCliente(String rut) throws Exception;
	
	// --------------------------------------------------------------------------------------------
	
	default ClienteDTO seleccionarCliente(ClienteDTO cliente) throws Exception
	{
		return this.seleccionarCliente(cliente.getRut());
	}
}
