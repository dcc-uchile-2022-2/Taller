package org.tds.sgh.business;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reserva {
	

	private Cliente cliente;	
	private TipoHabitacion tipoHabitacion;
	private Hotel hotel;
	private Huesped huesped;
	private int codigo;
	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	private boolean modificablePorHuesped;
	private EstadoReserva estado;		
	
	private Set<Huesped> huespedes;


	public Reserva(Cliente cliente, int codigo, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped, EstadoReserva estado) { 
		this.cliente = cliente;
		this.codigo = codigo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.modificablePorHuesped = modificablePorHuesped;
		this.estado=estado;		
		this.huespedes = new HashSet<Huesped>();
	}	
	



	
	public boolean coincide(String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		return this.tipoHabitacion.equals(nombreTipoHabitacion) &&
				this.fechaInicio.equals(fechaInicio) && 
				this.fechaFin.equals(fechaFin);
	}
	
	
	/*public List<Reserva> add(Reserva reserva){
		
		 
	}*/
	
	public boolean esDelCliente(Cliente cliente) {
		return this.cliente.equals(cliente);
		
	}
	public Set<Huesped> registrarHuesped(String nombre, String documento){
		
		Huesped h = new Huesped(nombre,documento);
		this.huespedes.add(h);
		return this.huespedes;
	}
	
	public void setEstado( EstadoReserva estado ) {
		this.estado = estado;
		return ;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public int getCodigo() {
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
	
	
}