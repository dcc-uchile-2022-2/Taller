package org.tds.sgh.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	
	public void registrarCliente(String rut, String nombre, String direccion, String telefono, String mail) {
		Cliente cliente = new Cliente(rut, nombre, direccion, telefono, mail);
		this.clientes.put(rut, cliente);
	}
	
	public List<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		
		List<Hotel> hotelesConDisp = new ArrayList<Hotel>();
		
		for (Hotel h: this.hotelesList) {
			boolean mismoPais = h.enPais(pais);
			if (mismoPais) {
				boolean disp = h.confirmarDisponibilidad(nombreTipoHabitacion, fechaInicio, fechaFin);
				if (disp) {
					hotelesConDisp.add(h);
				}
			}
		}
		
		return hotelesConDisp;
	}
	
	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		Hotel hotel = this.hoteles.get(nombreHotel);
		return  hotel.confirmarDisponibilidad(nombreTipoHabitacion, fechaInicio, fechaFin);;
	}
	
	public Reserva registrarReserva(String nombreHotel, String nombreTipoHabitacion, Cliente cliente, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		Hotel h = this.hoteles.get(nombreHotel);
		TipoHabitacion th = this.tiposHabitacion.get(nombreTipoHabitacion);
		Reserva r = h.crearReserva(th, cliente, fechaInicio, fechaFin, modificablePorHuesped);
		return r;

	}
	
	public List<Reserva> buscarReservasDelCliente(Cliente cliente) {
		List<Reserva> rs = new ArrayList<Reserva>();
		for (Hotel h: this.hotelesList) {
			Reserva resHotelCliente = h.buscarReservasCliente(cliente);
			rs.add(resHotelCliente);
		}
		return rs;
	}

	public Cliente buscarClientePorRut(String rut) {
		return this.buscarCliente(rut);
	}

	public Reserva modificarReserva(String nh, String nth, GregorianCalendar fi, GregorianCalendar ff, boolean mph) {
		Hotel h = this.hoteles.get(nh);
		TipoHabitacion th = this.tiposHabitacion.get(nth);
		Reserva r = h.crearReserva(th, this.cliente, fi, ff, mph);
		return r;
	}
	
		
}
