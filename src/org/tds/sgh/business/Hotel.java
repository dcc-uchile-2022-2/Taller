package org.tds.sgh.business;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tds.sgh.infrastructure.Infrastructure;


public class Hotel
{
	// --------------------------------------------------------------------------------------------
	
	 
	private Map<String, Habitacion> habitaciones  = new HashMap<String, Habitacion>();
	
	
	private String nombre;
	
	private String pais;
	
	private Map<String,TipoHabitacion> TiposHabitaciones =  new HashMap<String, TipoHabitacion>(); 
	private Set<Reserva> reservas  = new HashSet<Reserva>(); 


	
	// --------------------------------------------------------------------------------------------
	
	public Hotel(String nombre, String pais)
	{

		this.nombre = nombre;		
		this.pais = pais;
	
	}
	
	// --------------------------------------------------------------------------------------------
	
	public Habitacion agregarHabitacion(TipoHabitacion tipoHabitacion, String nombre) throws Exception
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
	}
	
	public boolean enPais(String pais) {
		return this.pais.equals(pais) ;
	}
	
	public boolean confirmarDisponibilidad(String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		
		int CantidadHabitaciones = 0;
		for( Habitacion h : this.habitaciones.values()) {
			if (h.getTipoHabitacion().getNombre().equals(nombreTipoHabitacion)) {
				CantidadHabitaciones++;
			}	
		}
		if (CantidadHabitaciones == 0) {
			return false;
		}
		int CantidadReservas =0;
		for( Reserva r : this.reservas) {
			if (!r.coincide(nombreTipoHabitacion,fechaInicio,fechaFin)) {
				CantidadReservas++;
			}
		}
		return CantidadReservas<CantidadHabitaciones;
	}
	
	public Reserva crearReserva(TipoHabitacion nombreTipoHabitacion, Cliente cliente, GregorianCalendar fechaInicio, GregorianCalendar fechaFin,
			boolean mph)  {
	
		Reserva reserva = new Reserva(this, cliente,nombreTipoHabitacion, fechaInicio, fechaFin, mph);
		

		this.reservas.add(reserva);
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(cliente.getMail(), "Reserva confirmada", "welcome");
		return reserva;
	}
	
	public Set<Reserva> buscarReservasCliente(Cliente c) {
		Set<Reserva> reservasHotelCliente = new HashSet<Reserva>();
		for ( Reserva reserva : this.reservas) {
			if (reserva.esDelCliente(c)) {
				reservasHotelCliente.add(reserva);
			}
		}
		return reservasHotelCliente;
	}
	
	

	public Reserva[] getReservas() {
		return this.getReservas();
	}
	
	public Reserva BuscarReservaPorCodigo(long codigo) {
		for (Reserva r :  this.reservas) {
			if (r.getCodigo()==codigo) {
				return r;
			}
		}
		return null;
	}
}
