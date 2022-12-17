package org.tds.sgh.dtos;

public class TipoHabitacionDTO
{
	// --------------------------------------------------------------------------------------------
	
	private String nombre;
	
	// --------------------------------------------------------------------------------------------
	
	public TipoHabitacionDTO(String nombre)
	{
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
		
		TipoHabitacionDTO that = (TipoHabitacionDTO) obj;
		
		if (!this.nombre.equals(that.nombre))
		{
			return false;
		}
		
		return true;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
			.append("TipoHabitacionDTO")
			.append(" { ")
			.append("nombre: ")
			.append(this.nombre)
			.append(" }")
			.toString();
	}
}
