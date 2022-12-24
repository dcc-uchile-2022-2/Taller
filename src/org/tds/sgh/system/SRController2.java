package org.tds.sgh.system;

import java.lang.reflect.Array;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.EstadoReserva;
import org.tds.sgh.business.Habitacion;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.ICalendario;
import org.tds.sgh.infrastructure.Infrastructure;

public class SRController2 extends SRController {
	
	public SRController2(CadenaHotelera ch) {
		super(ch);	
		this.ch=ch;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {				
		DTO dto = DTO.getInstance();
		Reserva r = this.ch.BuscarReservasPorCodigo(codigoReserva);		
		this.reserva = r;
		return dto.map(r);
	}
	

}
