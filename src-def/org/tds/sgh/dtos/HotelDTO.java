package org.tds.sgh.dtos;

public class HotelDTO
{
	// --------------------------------------------------------------------------------------------
	
	private String nombre;
	
	private String pais;
	
	// --------------------------------------------------------------------------------------------
	
	public HotelDTO(String nombre, String pais)
	{
		this.nombre = nombre;
		
		this.pais = pais;
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		
		HotelDTO that = (HotelDTO) obj;
		
		if (!this.nombre.equals(that.nombre) ||
			!this.pais.equals(that.pais))
		{
			return false;
		}
		
		return true;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getPais()
	{
		return this.pais;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
			.append("HotelDTO")
			.append(" { ")
			.append("nombre: ")
			.append(this.nombre)
			.append(", ")
			.append("país: ")
			.append(this.pais)
			.append(" }")
			.toString();
	}
}
