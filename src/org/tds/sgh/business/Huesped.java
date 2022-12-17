package org.tds.sgh.business;

public class Huesped {

	
	private String nombre;
	private String documento;
	
	public Huesped(String n, String d){
		this.nombre = n;
		this.documento = d;
	}
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getDocumento()
	{
		return this.documento;
	}

}
