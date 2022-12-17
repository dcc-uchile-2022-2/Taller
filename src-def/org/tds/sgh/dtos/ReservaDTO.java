package org.tds.sgh.dtos;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;


public class ReservaDTO
{
	// --------------------------------------------------------------------------------------------
	
	private long codigo;
	
	private String estado;
	
	private GregorianCalendar fechaFin;
	
	private GregorianCalendar fechaInicio;
	
	private String habitacion;
	
	private String hotel;
	
	private List<HuespedDTO> huespedes;
	
	private boolean modificablePorHuesped;
	
	private String rutCliente;
	
	private String tipoHabitacion;
	
	// --------------------------------------------------------------------------------------------
	
	public ReservaDTO(long codigo, String rutCliente, String hotel, String tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped, String estado, String habitacion, HuespedDTO... huespedes)
	{
		this.codigo = codigo;
		
		this.estado = estado;
		
		this.fechaFin = fechaFin;
		
		this.fechaInicio = fechaInicio;
		
		this.habitacion = habitacion;
		
		this.hotel = hotel;
		
		this.huespedes = huespedes == null
			? Collections.<HuespedDTO>emptyList()
			: Collections.unmodifiableList(Arrays.asList(huespedes));
		
		this.modificablePorHuesped = modificablePorHuesped;
		
		this.rutCliente = rutCliente;
		
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
		
		ReservaDTO that = (ReservaDTO) obj;
		
		if (this.codigo != that.codigo ||
			!this.estado.equals(that.estado) ||
			!this.fechaFin.equals(that.fechaFin) ||
			!this.fechaInicio.equals(that.fechaInicio) ||
			!this.hotel.equals(that.hotel) ||
			this.modificablePorHuesped != that.modificablePorHuesped ||
			!this.rutCliente.equals(that.rutCliente) ||
			!this.tipoHabitacion.equals(that.tipoHabitacion))
		{
			return false;
		}
		
		if ((this.habitacion == null && that.habitacion != null) ||
			(this.habitacion != null && that.habitacion == null) ||
			(this.habitacion != null && that.habitacion != null && !this.habitacion.equals(that.habitacion)))
		{
			return false;
		}
		
		if (this.huespedes.size() != that.huespedes.size())
		{
			return false;
		}
		else
		{
			for (HuespedDTO thisHuesped : this.huespedes)
			{
				if (!that.huespedes.contains(thisHuesped))
				{
					return false;
				}
			}
			
			for (HuespedDTO thatHuesped : that.huespedes)
			{
				if (!this.huespedes.contains(thatHuesped))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public long getCodigo()
	{
		return this.codigo;
	}
	
	public String getEstado()
	{
		return this.estado;
	}
	
	public GregorianCalendar getFechaFin()
	{
		return this.fechaFin;
	}
	
	public GregorianCalendar getFechaInicio()
	{
		return this.fechaInicio;
	}
	
	public String getHabitacion()
	{
		return this.habitacion;
	}
	
	public String getHotel()
	{
		return this.hotel;
	}
	
	public List<HuespedDTO> getHuespedes()
	{
		return this.huespedes;
	}
	
	public String getRutCliente()
	{
		return this.rutCliente;
	}
	
	public String getTipoHabitacion()
	{
		return this.tipoHabitacion;
	}
	
	public boolean isModificablePorHuesped()
	{
		return this.modificablePorHuesped;
	}
	
	@Override
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		
		StringBuilder sb = new StringBuilder()
			.append("ReservaDTO")
			.append(" { ")
			.append("código: ")
			.append(this.codigo)
			.append(", ")
			.append("hotel: ")
			.append(this.hotel)
			.append(", ")
			.append("fecha-inicio: ");
		
		sdf.setCalendar(this.fechaInicio);
		
		sb.append(sdf.format(this.fechaInicio.getTime()))
			.append(", ")
			.append("fecha-fin: ");
		
		sdf.setCalendar(this.fechaFin);
		
		sb.append(sdf.format(this.fechaFin.getTime()))
			.append(", ")
			.append("tipo: ")
			.append(this.tipoHabitacion)
			.append(", ")
			.append("rut-cliente: ")
			.append(this.rutCliente)
			.append(", ")
			.append("estado: ")
			.append(this.estado)
			.append(", ")
			.append("modificable: ")
			.append(this.modificablePorHuesped)
			.append(", ")
			.append("habitación: ")
			.append(this.habitacion == null ? "no-asignada" : this.habitacion)
			.append(", ")
			.append("huéspedes: ");
		
		if (this.huespedes.isEmpty())
		{
			sb.append("sin-huéspedes");
		}
		else
		{
			sb.append(this.huespedes.size())
				.append(" huésped")
				.append(this.huespedes.size() == 1 ? "" : "es")
				.append(": ");
			
			boolean first = true;
			
			for (HuespedDTO huesped : this.huespedes)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					sb.append(", ");
				}
				
				sb.append(" { ")
					.append("nombre: ")
					.append(huesped.getNombre())
					.append(", ")
					.append("documento: ")
					.append(huesped.getDocumento())
					.append(" }");
			}
		}
		
		sb.append(" }");
		
		return sb.toString();
	}
	
}
