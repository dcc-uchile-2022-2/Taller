package org.tds.sgh.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tds.sgh.infrastructure.ICalendario;
import org.tds.sgh.infrastructure.Infrastructure;

import java.util.GregorianCalendar;


public class CadenaHotelera
{
	// atributos
	
	private Map<String, Cliente> clientes;
	
	private Map<String, Hotel> hoteles;
	
	private String nombre;
	
	private Map<String, TipoHabitacion> tiposHabitacion;
	
	// Implementation
	private List<Hotel> hotelesList;
	private List<Cliente> clientesList;
	private List<Reserva> reservasList;
	private List<TipoHabitacion> tipoHabitacionList;
	
	
	// --------------------------------------------------------------------------------------------
	// Constructor
	
	public CadenaHotelera(String nombre)
	{
		this.clientes = new HashMap<String, Cliente>();
		
		this.hoteles = new HashMap<String, Hotel>();
		
		this.nombre = nombre;
		
		this.tiposHabitacion = new HashMap<String, TipoHabitacion>();
		
		this.hotelesList = new ArrayList<Hotel>();
		this.clientesList = new ArrayList<Cliente>();
		this.reservasList = new ArrayList<Reserva>();
		this.tipoHabitacionList = new ArrayList<TipoHabitacion>();
	}
	
	// metodos
	
	public Cliente agregarCliente(
		String rut,
		String nombre,
		String direccion,
		String telefono,
		String mail) throws Exception
	{
		if (this.clientes.containsKey(rut))
		{
			throw new Exception("Ya existe un cliente con el RUT indicado.");
		}
		
		Cliente cliente = new Cliente(rut, nombre, direccion, telefono, mail);
		
		this.clientes.put(cliente.getRut(), cliente);
		
		return cliente;
	}
	
	public Hotel agregarHotel(String nombre, String pais) throws Exception
	{
		if (this.hoteles.containsKey(nombre))
		{
			throw new Exception("Ya existe un hotel con el nombre indicado.");
		}
		
		Hotel hotel = new Hotel(nombre, pais);
		
		this.hoteles.put(hotel.getNombre(), hotel);
		
		return hotel;
	}
	
	public TipoHabitacion agregarTipoHabitacion(String nombre) throws Exception
	{
		if (this.tiposHabitacion.containsKey(nombre))
		{
			throw new Exception("Ya existe un tipo de habitación con el nombre indicado.");
		}
		
		TipoHabitacion tipoHabitacion = new TipoHabitacion(nombre);
		
		this.tiposHabitacion.put(tipoHabitacion.getNombre(), tipoHabitacion);
		
		return tipoHabitacion;
	}
	
	public Cliente buscarCliente(String rut) throws Exception
	{
		Cliente cliente = this.clientes.get(rut);
		
		if (cliente == null)
		{
			throw new Exception("No existe un cliente con el nombre indicado.");
		}
		
		return cliente;
	}
	
	public Set<Cliente> buscarClientes(String patronNombreCliente)
	{
		Set<Cliente> clientesEncontrados = new HashSet<Cliente>();
		
		for (Cliente cliente : this.clientes.values())
		{
			if (cliente.coincideElNombre(patronNombreCliente))
			{
				clientesEncontrados.add(cliente);
			}
		}
		
		return clientesEncontrados;
	}
	
	public Hotel buscarHotel(String nombre) throws Exception
	{
		Hotel hotel = this.hoteles.get(nombre);
		
		if (hotel == null)
		{
			throw new Exception("No existe un hotel con el nombre indicado.");
		}
		
		return hotel;
	}
	
	public TipoHabitacion buscarTipoHabitacion(String nombre) throws Exception
	{
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombre);
		
		if (tipoHabitacion == null)
		{
			throw new Exception("No existe un tipo de habitación con el nombre indicado.");
		}
		
		return tipoHabitacion;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public Set<Cliente> listarClientes()
	{
		return new HashSet<Cliente>(this.clientes.values());
	}
	
	public Set<Hotel> listarHoteles()
	{
		return new HashSet<Hotel>(this.hoteles.values());
	}
	
	public Set<TipoHabitacion> listarTiposHabitacion()
	{
		return new HashSet<TipoHabitacion>(this.tiposHabitacion.values());
	}
	
	// Implementación
	
	public Cliente registrarCliente(String rut, String nombre, String direccion, String telefono, String mail) throws Exception {
		
		boolean clienteYaExiste = this.clientes.containsKey(rut);
		if (clienteYaExiste) {
			throw new Exception();
		}
		
		
		Cliente cliente = new Cliente(rut, nombre, direccion, telefono, mail);
		this.clientes.put(rut, cliente);
		return cliente;
	}
	
	public Set<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		
		boolean existeTipoHabitacion = this.tiposHabitacion.containsKey(nombreTipoHabitacion);
		
		if (!existeTipoHabitacion) {
			throw new Exception();
		}
				
		Set<Hotel> hotelesConDisp = new HashSet<Hotel>();
		
		for (Hotel h: this.hoteles.values()) {
			if (!h.enPais(pais)) {
				continue ;
			}
			if(!h.confirmarDisponibilidad(nombreTipoHabitacion, fechaInicio, fechaFin)) {
				continue;
			}
		
			hotelesConDisp.add(h);			
		}
		
		return hotelesConDisp;
	}
	
	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception{
		
		boolean existeTipoHabitacion = this.tiposHabitacion.containsKey(nombreTipoHabitacion);
		if (!existeTipoHabitacion) {
			throw new Exception();
		}
		
		Hotel hotel = this.hoteles.get(nombreHotel);
		return  hotel.confirmarDisponibilidad(nombreTipoHabitacion, fechaInicio, fechaFin);
	}
	
	public Reserva registrarReserva( Cliente cliente, String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		Hotel h = this.hoteles.get(nombreHotel);
		TipoHabitacion th = this.tiposHabitacion.get(nombreTipoHabitacion);
		Reserva r = h.crearReserva(th, cliente, fechaInicio, fechaFin, modificablePorHuesped);
		return r;

	}
	
	public Set<Reserva> buscarReservasDelCliente(Cliente cliente) {
		
		ICalendario cal = Infrastructure.getInstance().getCalendario();
		
		Set<Reserva> rs = new HashSet<Reserva>();
		for (Hotel h: this.hoteles.values()) {
			Set<Reserva> resHotelCliente = h.buscarReservasCliente(cliente);
			rs.addAll(resHotelCliente);
		}
		
		for (Reserva r: rs) {
			boolean estaEnElPasado = cal.esPasada(r.getFechaFin());
			if (estaEnElPasado) {
				rs.remove(r);
			}
			
			boolean estaTomada = r.estado().equals(EstadoReserva.Tomada);
			if (estaTomada) {
				rs.remove(r);
			}
		}
		
		return rs;
	}
	
	public Set<Reserva> buscarReservasPendientes(String hotel) {
		Hotel h = this.hoteles.get(hotel);
		
		Set<Reserva> reservas = new HashSet<Reserva>();
		for (Reserva reserva : h.getReservas() ) {
			if (reserva.estado().equals(EstadoReserva.Pendiente )){
				reservas.add(reserva);
			}
		}
		return reservas;
	}

	public Cliente buscarClientePorRut(String rut) throws Exception {
		return this.buscarCliente(rut);
	}

	public Reserva modificarReserva(Reserva reserva, String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fi, GregorianCalendar ff, boolean mph) {

		// Remueve la reserva del hotel original. TODO: Mover a hotel esta responsabilidad.
		Hotel h = this.hoteles.get(reserva.getHotel().getNombre());
		h.getReservas().remove(reserva);
		
		reserva.setHotel(this.hoteles.get(nombreHotel));
		reserva.setTipoHabitacion(this.tiposHabitacion.get(nombreTipoHabitacion));
		reserva.setFechaInicio(fi);
		reserva.setFechaFin(ff);
		reserva.setMPH(mph);
		
		
		return reserva;
	
		//Hotel h = this.hoteles.get(nombreHotel);
		//for (Reserva reserva : h.getReservas() ) {
		//		//if ( reserva.coincide(nombreTipoHabitacion, ff, fi) ) {
		//		return reserva;
		//	//}			
		//}
		//return null;
	}
	
	public Reserva BuscarReservasPorCodigo(long codigo){
		
		Reserva r = null;
		for (Hotel h: this.hoteles.values()) {
			r = h.BuscarReservaPorCodigo(codigo); 
			if (r!=null) {
				break;
			}
		}
		return r;
	
	}

	public Reserva registrarHuesped(Reserva reserva, String nombre, String documento) {
		reserva.registrarHuesped(nombre, documento);
		return reserva;
		
	}
	
	
		
}
