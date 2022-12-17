package org.tds.sgh.test.stubs;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Consumer;

import org.tds.sgh.infrastructure.ICalendario;


public class CalendarioStub implements ICalendario
{
	// --------------------------------------------------------------------------------------------
	
	private GregorianCalendar hoy;
	
	// --------------------------------------------------------------------------------------------
	
	public CalendarioStub(Consumer<Consumer<GregorianCalendar>> ajustador)
	{
		this.hoy = new GregorianCalendar();
		
		ajustador.accept(this::setHoy);
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public boolean esAnterior(GregorianCalendar fecha1, GregorianCalendar fecha2)
	{
		return fecha1.get(Calendar.YEAR) < fecha2.get(Calendar.YEAR) ||
			(fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
				fecha1.get(Calendar.DAY_OF_YEAR) < fecha2.get(Calendar.DAY_OF_YEAR));
	}
	
	@Override
	public boolean esFutura(GregorianCalendar fecha)
	{
		return this.esPosterior(fecha, this.getHoy());
	}
	
	@Override
	public boolean esHoy(GregorianCalendar fecha)
	{
		return this.esMismoDia(fecha, this.getHoy());
	}
	
	@Override
	public boolean esMismoDia(GregorianCalendar fecha1, GregorianCalendar fecha2)
	{
		return fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
			fecha1.get(Calendar.DAY_OF_YEAR) == fecha2.get(Calendar.DAY_OF_YEAR);
	}
	
	@Override
	public boolean esPasada(GregorianCalendar fecha)
	{
		return this.esAnterior(fecha, this.getHoy());
	}
	
	@Override
	public boolean esPosterior(GregorianCalendar fecha1, GregorianCalendar fecha2)
	{
		return !this.esAnterior(fecha1, fecha2) && !this.esMismoDia(fecha1, fecha2);
	}
	
	@Override
	public GregorianCalendar getHoy()
	{
		return (GregorianCalendar) this.hoy.clone();
	}
	
	// --------------------------------------------------------------------------------------------
	
	private void setHoy(GregorianCalendar hoy)
	{
		this.hoy = (GregorianCalendar) hoy.clone();
	}
}
