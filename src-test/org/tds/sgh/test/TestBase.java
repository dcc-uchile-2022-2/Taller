package org.tds.sgh.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.HabitacionDTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.HuespedDTO;
import org.tds.sgh.dtos.TipoHabitacionDTO;
import org.tds.sgh.infrastructure.Infrastructure;
import org.tds.sgh.system.ControllerFactory;
import org.tds.sgh.system.IControllerFactory;
import org.tds.sgh.test.stubs.CalendarioStub;
import org.tds.sgh.test.stubs.SistemaFacturacionStub;
import org.tds.sgh.test.stubs.SistemaMensajeriaStub;


@RunWith(JUnit4.class)
public abstract class TestBase
{
	// --------------------------------------------------------------------------------------------
	
	protected static <T> List<T> asList(Set<T> set)
	{
		return set == null ? null : new ArrayList<T>(set);
	}
	
	// --------------------------------------------------------------------------------------------
	
	private static Random random = new Random();
	
	private static AtomicInteger lastId = new AtomicInteger(0);
	
	// --------------------------------------------------------------------------------------------
	
	protected CadenaHotelera cadenaHotelera;
	
	protected CalendarioStub calendario;
	
	protected Consumer<GregorianCalendar> calendarioCambiarHoy;
	
	protected IControllerFactory controllerFactory;
	
	protected SistemaMensajeriaStub sistemaMensajeria;
	
	protected SistemaFacturacionStub sistemaFacturacion;
	
	// --------------------------------------------------------------------------------------------
	
	@Before
	public void setUp() throws Exception
	{
		this.calendario = new CalendarioStub(c -> this.calendarioCambiarHoy = c);
		
		this.sistemaMensajeria = new SistemaMensajeriaStub();
		
		this.sistemaFacturacion = new SistemaFacturacionStub();
		
		Infrastructure.configure(this.sistemaFacturacion, this.sistemaMensajeria, this.calendario);
		
		this.cadenaHotelera = this.crearCadenaHotelera();
		
		this.controllerFactory = new ControllerFactory(this.cadenaHotelera);
	}
	
	@After
	public void tearDown() throws Exception
	{
		Infrastructure.clear();
	}
	
	// --------------------------------------------------------------------------------------------
	
	protected void cambiarHoyEnCalendario(GregorianCalendar fechaHoy)
	{
		this.calendarioCambiarHoy.accept(fechaHoy);
	}
	
	protected CadenaHotelera crearCadenaHotelera()
	{
		String id = this.generarId();
		
		return new CadenaHotelera("cadena" + id);
	}
	
	protected int generarInt()
	{
		return random.nextInt();
	}
	
	protected int generarInt(int maximum)
	{
		return random.nextInt(maximum + 1);
	}
	
	protected int generarInt(int minimum, int maximum)
	{
		return random.nextInt(maximum - minimum + 1) + minimum;
	}
	
	protected synchronized int generarIntId()
	{
		return lastId.addAndGet(this.generarInt(10) + 1);
	}
	
	protected String generarId()
	{
		return String.valueOf(this.generarIntId());
	}
	
	protected ClienteDTO generarCliente()
	{
		String id = this.generarId();
		
		return new ClienteDTO("rut" + id, "nombre" + id, "direccion" + id, "telefono" + id, "mail" + id);
	}
	
	protected ClienteDTO generarCliente(String rut)
	{
		String id = this.generarId();
		
		return new ClienteDTO(rut, "nombre" + id, "direccion" + id, "telefono" + id, "mail" + id);
	}
	
	protected ClienteDTO generarClienteConNombre(String nombre)
	{
		String id = this.generarId();
		
		return new ClienteDTO("rut" + id, nombre, "direccion" + id, "telefono" + id, "mail" + id);
	}
	
	protected HotelDTO generarHotel()
	{
		String id = this.generarId();
		
		return new HotelDTO("nombre" + id, "pais" + id);
	}
	
	protected HotelDTO generarHotel(String nombre)
	{
		String id = this.generarId();
		
		return new HotelDTO(nombre, "pais" + id);
	}
	
	protected HotelDTO generarHotelEnPais(String pais)
	{
		String id = this.generarId();
		
		return new HotelDTO("nombre" + id, pais);
	}
	
	protected HabitacionDTO generarHabitacion(HotelDTO hotel, TipoHabitacionDTO tipoHabitacion)
	{
		String id = this.generarId();
		
		return this.generarHabitacion(hotel, tipoHabitacion, "nombre" + id);
	}
	
	protected HabitacionDTO generarHabitacion(HotelDTO hotel, TipoHabitacionDTO tipoHabitacion, String nombre)
	{
		return new HabitacionDTO(hotel.getNombre(), tipoHabitacion.getNombre(), nombre);
	}
	
	protected TipoHabitacionDTO generarTipoHabitacion()
	{
		String id = this.generarId();
		
		return new TipoHabitacionDTO("nombre" + id);
	}
	
	protected TipoHabitacionDTO generarTipoHabitacion(String nombre)
	{
		return new TipoHabitacionDTO(nombre);
	}
	
	protected HuespedDTO generarHuesped()
	{
		String id = generarId();
		
		return new HuespedDTO("nombre" + id, "documento" + id);
	}
	
	protected GregorianCalendar generarFecha()
	{
		return generarFecha(this.calendario.getHoy());
	}
	
	protected GregorianCalendar generarFecha(GregorianCalendar fechaDesde)
	{
		return this.generarFecha(fechaDesde, 1, this.generarInt(1, 33));
	}
	
	protected GregorianCalendar generarFecha(GregorianCalendar fechaDesde, int cantidadMaximaDeDias)
	{
		return this.generarFecha(fechaDesde, 1, cantidadMaximaDeDias);
	}
	
	protected GregorianCalendar generarFecha(
		GregorianCalendar fechaDesde,
		int cantidadMinimaDeDias,
		int cantidadMaximaDeDias)
	{
		GregorianCalendar fecha = (GregorianCalendar) fechaDesde.clone();
		
		fecha.add(
			Calendar.DAY_OF_YEAR,
			this.generarInt(cantidadMaximaDeDias - cantidadMinimaDeDias) + cantidadMinimaDeDias + 1);
		
		return fecha;
	}
	
	protected GregorianCalendar generarFechaEnElPasado()
	{
		GregorianCalendar fecha = new GregorianCalendar();
		
		fecha.add(Calendar.DAY_OF_YEAR, -this.generarInt(33) - 1);
		
		return fecha;
	}
}
