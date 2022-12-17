package org.tds.sgh.business;

public class Huesped {

	// ----------------------------------------------------------------------------------
	private String nombre;
	private String documento;
	
	// ----------------------------------------------------------------------------------
	public Huesped(String nombre, String documento)
	{
		this.nombre = nombre;
		this.documento = documento;
	}
	
	// --------------------------------------------------------------------------------------------
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getDocumento()
	{
		return this.documento;
	}
}
