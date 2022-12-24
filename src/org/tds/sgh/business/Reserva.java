package org.tds.sgh.business;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.tds.sgh.infrastructure.ICalendario;
import org.tds.sgh.infrastructure.Infrastructure;


public class Reserva {
	

	private Cliente cliente;	
	private TipoHabitacion tipoHabitacion;
	private Hotel hotel;
	private Huesped huesped;
	private long codigo;
	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	private boolean modificablePorHuesped;
	private EstadoReserva estado;		
	private Habitacion habitacion;
	private Set<Huesped> huespedes;
    
	private static AtomicLong CodigoDisponible = new AtomicLong(0);

	public Reserva(Hotel hotel,Cliente cliente,TipoHabitacion tipoHabitacion , GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) { 
		this.cliente = cliente;
		this.codigo = CodigoDisponible.getAndIncrement();
		
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.modificablePorHuesped = modificablePorHuesped;
		this.estado=EstadoReserva.Pendiente;
		this.tipoHabitacion = tipoHabitacion;
		this.huespedes = new HashSet<Huesped>();
		this.hotel = hotel;
	}	
	

	
	public boolean coincide(String nombreTipoHabitacion , GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		
		ICalendario cal = Infrastructure.getInstance().getCalendario();
		
		//System.out.println(nombreTipoHabitacion);
		//System.out.println(this.tipoHabitacion.getNombre());
		//System.out.println(this.tipoHabitacion.getNombre().equals(nombreTipoHabitacion));
		
		//boolean _coincide = this.tipoHabitacion.getNombre().equals(nombreTipoHabitacion) && this.estado.equals(EstadoReserva.Pendiente) &&
		//		!(cal.esAnterior(this.fechaInicio, fechaFin) ||
		//		cal.esPosterior(this.fechaFin, fechaInicio)) && 
		//		(cal.esMismoDia(this.fechaFin, fechaInicio) ||
		//		 cal.esMismoDia(this.fechaInicio, fechaFin));
		
		boolean coincideTipoHabitacion = this.tipoHabitacion.getNombre().equals(nombreTipoHabitacion);
		boolean esEstadoReservaPendiente = this.estado.equals(EstadoReserva.Pendiente);
		boolean intersectaReserva = !(cal.esPosterior(this.fechaInicio, fechaFin) || cal.esAnterior(this.fechaFin, fechaInicio));
		boolean noEsReservaConsecutiva = !(cal.esMismoDia(this.fechaFin, fechaInicio) || cal.esMismoDia(this.fechaInicio, fechaFin));
				
		//boolean _coincide = this.tipoHabitacion.getNombre().equals(nombreTipoHabitacion) && this.estado.equals(EstadoReserva.Pendiente) &&
		//		!(cal.esPosterior(this.fechaInicio, fechaFin) ||
		//		cal.esAnterior(this.fechaFin, fechaInicio));
		
		boolean _coincide = coincideTipoHabitacion && esEstadoReservaPendiente && intersectaReserva && noEsReservaConsecutiva;
		
		//System.out.println(_coincide);
		
		return _coincide;
		
				
	}
	public Habitacion getHabitacion(){
		return this.habitacion;
	}
	
	public Hotel getHotel() {
		return this.hotel;
	}
	
	public String getTipoReserva() {
		return this.estado.toString();
	}

	
	public boolean esDelCliente(Cliente cliente) {
		return this.cliente.equals(cliente);
		
	}
	public Set<Huesped> registrarHuesped(String nombre, String documento){
		
		Huesped h = new Huesped(nombre,documento);
		this.huespedes.add(h);
		return this.huespedes;
	}
	
	public Set<Huesped> getHuespedes(){
		return this.huespedes;
	}
	
	public void setEstado( EstadoReserva estado ) {
		this.estado = estado;
		return ;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public long getCodigo() {
		return this.codigo;		
	}
	
	public GregorianCalendar getFechaInicio() {
		return this.fechaInicio;
	}
	
	public GregorianCalendar getFechaFin() {
		return this.fechaFin;		
	}
	
	public EstadoReserva estado() {
		return this.estado;
	}
	
	public boolean getModificablePorHuesped() {
		return this.modificablePorHuesped ;
	}


	public void TomarReserva(){
		this.hotel.BuscarHabitacionDisponible(this);
		this.estado= EstadoReserva.Tomada;
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(this.cliente.getMail(), "Tomada", "Ingresaste ql");
		
	}

	public TipoHabitacion getTipoHabitacion() {
		return this.tipoHabitacion;
	}
	
	public void SetHabitacion(Habitacion habitacion) {
		this.habitacion= habitacion;
	}

	public void setHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		this.hotel = hotel;
	}

	public void setTipoHabitacion(TipoHabitacion th) {
		// TODO Auto-generated method stub
		this.tipoHabitacion = th;
	}

	public void setFechaInicio(GregorianCalendar fi) {
		// TODO Auto-generated method stub
		this.fechaInicio = fi;
	}

	public void setFechaFin(GregorianCalendar ff) {
		// TODO Auto-generated method stub
		this.fechaFin = ff;
	}

	public void setMPH(boolean mph) {
		// TODO Auto-generated method stub
		this.modificablePorHuesped = mph;
	}
}