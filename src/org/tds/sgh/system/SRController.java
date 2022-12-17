package org.tds.sgh.system;

import java.lang.reflect.Array;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Habitacion;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.Infrastructure;

public class SRController implements IHacerReservaController {
	
	private CadenaHotelera ch;
	private Cliente cliente;
	private HashMap<String,Cliente> clienteMap;
	private Set<Cliente> clientes;
	
	public  SRController(CadenaHotelera ch){
		this.ch = ch;
	}


	@Override
	public Set<ClienteDTO> buscarCliente(String patronNombreCliente) {
		DTO dto = DTO.getInstance();		
		Set<Cliente> clientes = this.ch.buscarClientes(patronNombreCliente);
		this.clientes=clientes;
		return dto.mapClientes(clientes);
	}


	@Override
	public ClienteDTO seleccionarCliente(String rut) throws Exception {
		DTO dto = DTO.getInstance();		

		Cliente cliente = this.ch.buscarClientePorRut(rut);
		this.cliente = cliente;
		return dto.map(cliente);
	}


	@Override
	public ClienteDTO registrarCliente(String rut, String nombre, String direccion, String telefono, String mail) 
			throws Exception {
		DTO dto = DTO.getInstance();		
		Cliente cliente = this.ch.registrarCliente(rut, nombre, direccion, telefono, mail);
		this.cliente=cliente;
		return dto.map(cliente);
	}


	@Override
	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		
		return 	this.ch.confirmarDisponibilidad(nombreHotel, nombreTipoHabitacion, fechaInicio, fechaFin);
	}


	@Override
	public ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		DTO dto = DTO.getInstance();				
		return  dto.map(this.ch.registrarReserva(this.cliente,nombreHotel, nombreTipoHabitacion, fechaInicio, fechaFin,modificablePorHuesped));

	}


	@Override
	public Set<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		DTO dto = DTO.getInstance();		
		Set<Hotel> hoteles =  this.ch.sugerirAlternativas(pais, nombreTipoHabitacion, fechaInicio, fechaFin);
		return dto.mapHoteles(hoteles);

	}

}
