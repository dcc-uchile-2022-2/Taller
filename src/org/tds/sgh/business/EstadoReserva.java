package org.tds.sgh.business;



public enum EstadoReserva {
Pendiente,
Tomada,
Finalizada,
Cancelada,
NoTomada;

public String obtenerEstado() {

    switch(this) {
      case Pendiente:
        return "Pendiente";

      case Tomada:
        return "Tomada";

      case Finalizada:
        return "Finalizada";

      case Cancelada:
        return "Cancelada";
        
      case NoTomada:
    	  return "No Tomada";
    	  
    	  default:
        return null;
      }
}
}
