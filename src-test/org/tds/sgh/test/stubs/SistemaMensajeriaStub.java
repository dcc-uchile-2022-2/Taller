package org.tds.sgh.test.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tds.sgh.infrastructure.ISistemaMensajeria;


public class SistemaMensajeriaStub implements ISistemaMensajeria
{
	// --------------------------------------------------------------------------------------------
	
	private List<Mail> mails;
	
	// --------------------------------------------------------------------------------------------
	
	public SistemaMensajeriaStub()
	{
		this.mails = new ArrayList<Mail>();
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public void enviarMail(String destinatario, String asunto, String texto)
	{
		this.mails.add(new Mail(destinatario, asunto, texto));
	}
	
	public List<Mail> getMailEnviados()
	{
		return Collections.unmodifiableList(this.mails);
	}
	
	// ============================================================================================
	
	public static class Mail
	{
		// --------------------------------------------------------------------------------------------
		
		private String asunto;
		
		private String destinatario;
		
		private String texto;
		
		// --------------------------------------------------------------------------------------------
		
		public Mail(String destinatario, String asunto, String texto)
		{
			this.asunto = asunto;
			
			this.destinatario = destinatario;
			
			this.texto = texto;
		}
		
		// --------------------------------------------------------------------------------------------
		
		public String getAsunto()
		{
			return this.asunto;
		}
		
		public String getDestinatario()
		{
			return this.destinatario;
		}
		
		public String getTexto()
		{
			return this.texto;
		}
	}
}
