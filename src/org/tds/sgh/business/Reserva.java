package org.tds.sgh.business;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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


	public Reserva(Cliente cliente, int codigo, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped, EstadoReserva estado) { 
		this.cliente = cliente;
		this.codigo = codigo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.modificablePorHuesped = modificablePorHuesped;
		this.estado=estado;				 
	}	
	


	public Reserva create(String tipoHabitacion, Cliente cliente, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		return hotel.crearReserva(tipoHabitacion, cliente, fechaInicio, fechaFin, modificablePorHuesped);		
	}

	
	public boolean coincide(String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		
	}
	
	
	public List<Reserva> add(Reserva reserva){
		
		 
	}
	
	public boolean esDelCliente(Cliente cliente) {
		
	}
	
	public List<Huesped> registrarHuesped(String nombre, String documento){
		
		
		
	}
	
	public String setEstado(String stado) {
		
		
		
	}
	
	public void iniciarEstadia() {
		
		
	}
	
	
	public void iniciarFacturacion() {
		
		
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
