package org.tds.sgh.infrastructure;

import java.util.GregorianCalendar;


public interface ICalendario
{
	// --------------------------------------------------------------------------------------------
	
	boolean esAnterior(GregorianCalendar fecha1, GregorianCalendar fecha2);
	
	boolean esFutura(GregorianCalendar fecha);
	
	boolean esHoy(GregorianCalendar fecha);
	
	boolean esMismoDia(GregorianCalendar fecha1, GregorianCalendar fecha2);
	
	boolean esPasada(GregorianCalendar fecha);
	
	boolean esPosterior(GregorianCalendar fecha1, GregorianCalendar fecha2);
	
	GregorianCalendar getHoy();
}
