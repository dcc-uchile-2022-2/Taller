package org.tds.sgh.dtos;

public class HuespedDTO
{
	// --------------------------------------------------------------------------------------------
	
	private String documento;
	
	private String nombre;
	
	// --------------------------------------------------------------------------------------------
	
	public HuespedDTO(String nombre, String documento)
	{
		this.documento = documento;
		
		this.nombre = nombre;
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
		
		HuespedDTO that = (HuespedDTO) obj;
		
		if (!this.documento.equals(that.documento) ||
			!this.nombre.equals(that.nombre))
		{
			return false;
		}
		
		return true;
	}
	
	public String getDocumento()
	{
		return this.documento;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
			.append("HuespedDTO")
			.append(" { ")
			.append("nombre: ")
			.append(this.nombre)
			.append(", ")
			.append("documento: ")
			.append(this.documento)
			.append(" }")
			.toString();
	}
}
