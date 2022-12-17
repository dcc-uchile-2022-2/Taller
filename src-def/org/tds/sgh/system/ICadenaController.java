package org.tds.sgh.system;

import java.util.Set;

import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;


public interface ICadenaController
{
	// --------------------------------------------------------------------------------------------
	
	ClienteDTO agregarCliente(
		String rut,
		String nombre,
		String direccion,
		String telefono,
		String mail) throws Exception;
	
	HabitacionDTO agregarHabitacion(String nombreHotel, String nombreTipoHabitacion, String nombre) throws Exception;
	
	HotelDTO agregarHotel(String nombre, String pais) throws Exception;
	
	TipoHabitacionDTO agregarTipoHabitacion(String nombre) throws Exception;
	
	Set<ClienteDTO> getClientes();
	
	Set<HabitacionDTO> getHabitaciones(String nombreHotel) throws Exception;
	
	Set<HotelDTO> getHoteles();
	
	Set<TipoHabitacionDTO> getTiposHabitacion();
	
	// --------------------------------------------------------------------------------------------
	
	default ClienteDTO agregarCliente(ClienteDTO cliente) throws Exception
	{
		return this.agregarCliente(
			cliente.getRut(),
			cliente.getNombre(),
			cliente.getDireccion(),
			cliente.getTelefono(),
			cliente.getMail());
	}
	
	default HabitacionDTO agregarHabitacion(HabitacionDTO habitacion) throws Exception
	{
		return this.agregarHabitacion(habitacion.getHotel(), habitacion.getTipoHabitacion(), habitacion.getNombre());
	}
	
	default HotelDTO agregarHotel(HotelDTO hotel) throws Exception
	{
		return this.agregarHotel(hotel.getNombre(), hotel.getPais());
	}
	
	default TipoHabitacionDTO agregarTipoHabitacion(TipoHabitacionDTO tipoHabitacion) throws Exception
	{
		return this.agregarTipoHabitacion(tipoHabitacion.getNombre());
	}
	
	default Set<HabitacionDTO> getHabitaciones(HotelDTO hotel) throws Exception
	{
		return this.getHabitaciones(hotel.getNombre());
	}
}
