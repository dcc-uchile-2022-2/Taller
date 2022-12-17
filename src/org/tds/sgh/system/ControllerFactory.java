package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.infrastructure.NotImplementedException;


public class ControllerFactory implements IControllerFactory
{
	// --------------------------------------------------------------------------------------------
	
	private CadenaHotelera cadenaHotelera;
	
	// --------------------------------------------------------------------------------------------
	
	public ControllerFactory(CadenaHotelera cadenaHotelera)
	{
		this.cadenaHotelera = cadenaHotelera;
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public ICadenaController createCadenaController()
	{
		return new CadenaController(this.cadenaHotelera);
	}
	
	@Override
	public ICancelarReservaController createCancelarReservaController()
	{
		// TODO
		throw new NotImplementedException();
	}
	
	@Override
	public IHacerReservaController createHacerReservaController()
	{
		// TODO
		throw new NotImplementedException();
	}
	
	@Override
	public IModificarReservaController createModificarReservaController()
	{
		// TODO
		throw new NotImplementedException();
	}
	
	@Override
	public ITomarReservaController createTomarReservaController()
	{
		// TODO
		throw new NotImplementedException();
	}
}
