package org.tds.sgh.business;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Hotel
{
	
	
	private Collection<Habitacion> habitaciones;	
	
	private Collection<Reserva> reservas;
	
	private String nombre;
	
	private String pais;
		
	
	public Hotel(String nombre, String pais)
	{
		this.habitaciones = new HashSet<Habitacion>();
		
		this.reservas = new HashSet<Reserva>();
		
		this.nombre = nombre;
		
		this.pais = pais;
	}
	
	
	
	public boolean confirmarDisponibilidad(String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		//falta logica		
		return true;
	}
	
	
	public Reserva crearReserva(String tipoHabitacion, Cliente cliente, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		//falta logica		
		return reserva
	}
	
	
	public Hotel find(String nombreHotel) {
		//falta logica
		return hotel;
	}
	
	public boolean enPais(boolean pais) {
		//falta logica
		return true;
	}
	
	public List<Reserva> buscarReservasCliente(){
		//falta logica
		return reservas;
	}
	
	
	
}
	
	
	
	
	
	// --------------------------------------------------------------------------------------------
	
	/*public Habitacion agregarHabitacion(TipoHabitacion tipoHabitacion, String nombre) throws Exception
	{
		if (this.habitaciones.containsKey(nombre))
		{
			throw new Exception("El hotel ya tiene una habitación con el nombre indicado.");
		}
		
		Habitacion habitacion = new Habitacion(tipoHabitacion, nombre);
		
		this.habitaciones.put(habitacion.getNombre(), habitacion);
		
		return habitacion;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getPais()
	{
		return this.pais;
	}
	
	public Set<Habitacion> listarHabitaciones()
	{
		return new HashSet<Habitacion>(this.habitaciones.values());
	}*/

