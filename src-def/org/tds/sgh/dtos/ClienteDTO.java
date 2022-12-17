package org.tds.sgh.dtos;

public class ClienteDTO
{
	// --------------------------------------------------------------------------------------------
	
	private String direccion;
	
	private String mail;
	
	private String nombre;
	
	private String rut;
	
	private String telefono;
	
	// --------------------------------------------------------------------------------------------
	
	public ClienteDTO(String rut, String nombre, String direccion, String telefono, String mail)
	{
		this.direccion = direccion;
		
		this.mail = mail;
		
		this.nombre = nombre;
		
		this.rut = rut;
		
		this.telefono = telefono;
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
		
		ClienteDTO that = (ClienteDTO) obj;
		
		if (!this.direccion.equals(that.direccion) ||
			!this.mail.equals(that.mail) ||
			!this.nombre.equals(that.nombre) ||
			!this.rut.equals(that.rut) ||
			!this.telefono.equals(that.telefono))
		{
			return false;
		}
		
		return true;
	}
	
	public String getDireccion()
	{
		return this.direccion;
	}
	
	public String getMail()
	{
		return this.mail;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getRut()
	{
		return this.rut;
	}
	
	public String getTelefono()
	{
		return this.telefono;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
			.append("ClienteDTO")
			.append(" { ")
			.append("nombre: ")
			.append(this.nombre)
			.append(", ")
			.append("rut: ")
			.append(this.rut)
			.append(", ")
			.append("mail: ")
			.append(this.mail)
			.append(", ")
			.append("telefono: ")
			.append(this.telefono)
			.append(", ")
			.append("dirección: ")
			.append(this.direccion)
			.append(" }")
			.toString();
	}
}
