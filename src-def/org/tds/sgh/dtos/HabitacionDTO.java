package org.tds.sgh.dtos;

public class HabitacionDTO
{
	// --------------------------------------------------------------------------------------------
	
	private String hotel;
	
	private String nombre;
	
	private String tipoHabitacion;
	
	// --------------------------------------------------------------------------------------------
	
	public HabitacionDTO(String hotel, String tipoHabitacion, String nombre)
	{
		this.hotel = hotel;
		
		this.nombre = nombre;
		
		this.tipoHabitacion = tipoHabitacion;
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
		
		HabitacionDTO that = (HabitacionDTO) obj;
		
		if (!this.hotel.equals(that.hotel) ||
			!this.nombre.equals(that.nombre) ||
			!this.tipoHabitacion.equals(that.tipoHabitacion))
		{
			return false;
		}
		
		return true;
	}
	
	public String getHotel()
	{
		return this.hotel;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getTipoHabitacion()
	{
		return tipoHabitacion;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
			.append("HabitacionDTO")
			.append(" { ")
			.append("hotel: ")
			.append(this.hotel)
			.append(", ")
			.append("nombre: ")
			.append(this.nombre)
			.append(", ")
			.append("tipo: ")
			.append(this.tipoHabitacion)
			.append(" }")
			.toString();
	}
}
