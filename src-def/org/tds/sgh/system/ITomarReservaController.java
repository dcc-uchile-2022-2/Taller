package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.Set;

import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.HuespedDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;


public interface ITomarReservaController extends IIdentificarReservaClienteController, IHacerReservaController, IModificarReservaController
{
	// --------------------------------------------------------------------------------------------
	
	Set<ReservaDTO> buscarReservasPendientes(String nombreHotel) throws Exception;
	
	ReservaDTO seleccionarReserva(long codigoReserva) throws Exception;
	
	ReservaDTO registrarHuesped(String nombre, String documento) throws Exception;
	
	ReservaDTO tomarReserva() throws Exception;
	
	// --------------------------------------------------------------------------------------------
	
	default Set<ReservaDTO> buscarReservasPendientes(HotelDTO hotel) throws Exception
	{
		return this.buscarReservasPendientes(hotel.getNombre());
	}
	
	@Override
	public default boolean confirmarDisponibilidad(
		HotelDTO hotel,
		TipoHabitacionDTO tipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin) throws Exception
	{
		return IHacerReservaController.super.confirmarDisponibilidad(hotel, tipoHabitacion, fechaInicio, fechaFin);
	}
	
	default ReservaDTO registrarHuesped(HuespedDTO huesped) throws Exception
	{
		return this.registrarHuesped(huesped.getNombre(), huesped.getDocumento());
	}
	
	default ReservaDTO seleccionarReserva(ReservaDTO reserva) throws Exception
	{
		return this.seleccionarReserva(reserva.getCodigo());
	}
	
	@Override
	public default Set<HotelDTO> sugerirAlternativas(
		String pais,
		TipoHabitacionDTO tipoHabitacion,
		GregorianCalendar fechaInicio,
		GregorianCalendar fechaFin) throws Exception
	{
		return IHacerReservaController.super.sugerirAlternativas(pais, tipoHabitacion, fechaInicio, fechaFin);
	}
}
