package org.tds.sgh.test.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.ISistemaFacturacion;


public class SistemaFacturacionStub implements ISistemaFacturacion
{
	// --------------------------------------------------------------------------------------------
	
	private List<ReservaDTO> reservas;
	
	// --------------------------------------------------------------------------------------------
	
	public SistemaFacturacionStub()
	{
		this.reservas = new ArrayList<ReservaDTO>();
	}
	
	// --------------------------------------------------------------------------------------------
	
	public List<ReservaDTO> getReservas()
	{
		return Collections.unmodifiableList(this.reservas);
	}
	
	@Override
	public void iniciarEstadia(ReservaDTO reserva)
	{
		this.reservas.add(reserva);
	}
}
