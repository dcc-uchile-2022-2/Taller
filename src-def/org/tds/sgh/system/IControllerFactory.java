package org.tds.sgh.system;

public interface IControllerFactory
{
	// --------------------------------------------------------------------------------------------
	
	ICadenaController createCadenaController();
	
	ICancelarReservaController createCancelarReservaController();
	
	IHacerReservaController createHacerReservaController();
	
	IModificarReservaController createModificarReservaController();
	
	ITomarReservaController createTomarReservaController();
}
