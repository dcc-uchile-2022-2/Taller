package org.tds.sgh.infrastructure;

public class Infrastructure
{
	// --------------------------------------------------------------------------------------------
	
	private static final ThreadLocal<Infrastructure> Instance = new ThreadLocal<Infrastructure>();
	
	// --------------------------------------------------------------------------------------------
	
	public static void clear()
	{
		Instance.set(null);
	}
	
	public static void configure(
		ISistemaFacturacion sistemaFacturacion,
		ISistemaMensajeria sistemaMensajeria,
		ICalendario calendario)
	{
		Infrastructure instance = new Infrastructure();
		
		instance.calendario = calendario;
		
		instance.sistemaFacturacion = sistemaFacturacion;
		
		instance.sistemaMensajeria = sistemaMensajeria;
		
		Instance.set(instance);
	}
	
	public static Infrastructure getInstance()
	{
		return Instance.get();
	}
	
	// --------------------------------------------------------------------------------------------
	
	private ICalendario calendario;
	
	private ISistemaFacturacion sistemaFacturacion;
	
	private ISistemaMensajeria sistemaMensajeria;
	
	// --------------------------------------------------------------------------------------------
	
	private Infrastructure()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	
	public ICalendario getCalendario()
	{
		return this.calendario;
	}
	
	public ISistemaFacturacion getSistemaFacturacion()
	{
		return this.sistemaFacturacion;
	}
	
	public ISistemaMensajeria getSistemaMensajeria()
	{
		return this.sistemaMensajeria;
	}
}
