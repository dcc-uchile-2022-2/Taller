package org.tds.sgh.business;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Reserva {
	

	private Cliente cliente;
	private int codigo;
	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	private boolean modificablePorHuesped;
	private EstadoReserva estado;		
<<<<<<< HEAD

=======

>>>>>>> origin/AlejandroCortes
	public Reserva(Cliente cliente, int codigo, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped, EstadoReserva estado) {
		// TODO Auto-generated constructor stub
		this.cliente = cliente;
		this.codigo = codigo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.modificablePorHuesped = modificablePorHuesped;
		this.estado=estado;				 
	}	
<<<<<<< HEAD
	
	public Reserva create(String tipoHabitacion, Cliente cliente, Date fechaInicio, Date fechaFin, boolean modificablePorHuesped) {

	}
	
	public boolean coincide(String nombreTipoHabitacion, Date fechaInicio, Date fechaFin) {
		
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
	

}
=======

	public Reserva create(String tipoHabitacion, Cliente cliente, Date fechaInicio, Date fechaFin, boolean modificablePorHuesped) {
			return null;
	}

	public boolean coincide(String nombreTipoHabitacion, Date fechaInicio, Date fechaFin) {
		return false;
	}


	public List<Reserva> add(Reserva reserva){

		return null;

	}

	public boolean esDelCliente(Cliente cliente) {
			
		return false;
		
	}

	public List<Huesped> registrarHuesped(String nombre, String documento){

		return null;
	}

	public String setEstado(String stado) {

		return null;
	}

	public void iniciarEstadia() {

		return;

	}


	public void iniciarFacturacion() {

		return;

	}


}
>>>>>>> origin/AlejandroCortes
