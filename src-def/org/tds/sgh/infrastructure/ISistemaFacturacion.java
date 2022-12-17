package org.tds.sgh.infrastructure;

import org.tds.sgh.dtos.ReservaDTO;


public interface ISistemaFacturacion
{
	// --------------------------------------------------------------------------------------------
	
	void iniciarEstadia(ReservaDTO reserva);
}
