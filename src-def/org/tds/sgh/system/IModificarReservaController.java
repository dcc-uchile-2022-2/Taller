package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.Set;

import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;


public interface IModificarReservaController extends IIdentificarClienteEnRecepcionController, IIdentificarReservaClienteController
{
	// --------------------------------------------------------------------------------------------
	
	boolean confirmarDisponibilidad(
		String nombreHotel,
		String nombreTipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin) throws Exception;
	
	ReservaDTO modificarReserva(
		String nombreHotel,
		String nombreTipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin,
		boolean modificablePorHuesped) throws Exception;
	
	Set<HotelDTO> sugerirAlternativas(
		String pais,
		String nombreTipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin) throws Exception;
	
	// --------------------------------------------------------------------------------------------
	
	default boolean confirmarDisponibilidad(
		HotelDTO hotel,
		TipoHabitacionDTO tipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin) throws Exception
	{
		return this.confirmarDisponibilidad(hotel.getNombre(), tipoHabitacion.getNombre(), fechaInicio, fechaFin);
	}
	
	default ReservaDTO modificarReserva(
		HotelDTO hotel,
		TipoHabitacionDTO tipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin,
		boolean modificablePorHuesped) throws Exception
	{
		return this.modificarReserva(
			hotel.getNombre(),
			tipoHabitacion.getNombre(),
			fechaInicio,
			fechaFin,
			modificablePorHuesped);
	}
	
	default Set<HotelDTO> sugerirAlternativas(
		String pais,
		TipoHabitacionDTO tipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin) throws Exception
	{
		return this.sugerirAlternativas(pais, tipoHabitacion.getNombre(), fechaInicio, fechaFin);
	}
}
