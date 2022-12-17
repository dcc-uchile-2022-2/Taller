package org.tds.sgh.system;

import java.util.Set;

import org.tds.sgh.dtos.ReservaDTO;


public interface IIdentificarReservaClienteController extends IIdentificarClienteEnRecepcionController
{
	// --------------------------------------------------------------------------------------------
	
	Set<ReservaDTO> buscarReservasDelCliente() throws Exception;
	
	ReservaDTO seleccionarReserva(long codigoReserva) throws Exception;
	
	// --------------------------------------------------------------------------------------------
	
	default ReservaDTO seleccionarReserva(ReservaDTO reserva) throws Exception
	{
		return this.seleccionarReserva(reserva.getCodigo());
	}
}
