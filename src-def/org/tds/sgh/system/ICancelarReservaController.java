package org.tds.sgh.system;

import org.tds.sgh.dtos.ReservaDTO;


public interface ICancelarReservaController extends IIdentificarClienteEnRecepcionController, IIdentificarReservaClienteController
{
	// --------------------------------------------------------------------------------------------
	
	ReservaDTO cancelarReservaDelCliente() throws Exception;
}
