package org.tds.sgh.infrastructure;

public interface ISistemaMensajeria
{
	// --------------------------------------------------------------------------------------------
	
	void enviarMail(String destinatario, String asunto, String mensaje);
}
