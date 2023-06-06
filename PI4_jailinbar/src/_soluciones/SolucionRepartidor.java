package _soluciones;

import java.util.List;

import _datos.DatosRepartidor;

public class SolucionRepartidor {

	// Propiedades
	private List<Integer> camino;
	private Double distancia;
	private Double beneficio;
	
	// Metodo de factoria
	public static SolucionRepartidor of_Permut(List<Integer> ls) {
		return new SolucionRepartidor(ls);
	}
	
	// Constructor
	public SolucionRepartidor(List<Integer> ls) {
		Double km = 0.;
		Double beneficios = 0.;

		// Recorremos el cromosoma para hallar la distancia recorrida y el beneficio
		for(int i=0; i<ls.size(); i++) {
			// Beneficio += beneficio - cada minuto que se pase (ponemos los km, ya que tarda 1 minuto en hacer 1km)
			// Si no existe la arista aumentamos el error
			if(i==0) {
				km += DatosRepartidor.getPesoArista(0, ls.get(i));
				beneficios += DatosRepartidor.getBeneficioCliente(ls.get(i)) - km;
			} else {
				km += DatosRepartidor.getPesoArista(ls.get(i-1), ls.get(i));
				beneficios += DatosRepartidor.getBeneficioCliente(ls.get(i)) - km;
			}
		}
		// Añadimos a la posicion 0 el inicial, es decir, el 0
		ls.add(0, 0);
		
		camino = ls;
		distancia = km;
		beneficio = beneficios;
	}
	
	// Metodo toString que devuelve una cadena con el orden de clientes que ha seguido, 
	// la distancia recorrida en km y el beneficio total
	@Override
	public String toString() {
		return String.format("- Camino desde 0 hasta 0: %s;\n- Kms: %f;\n- Beneficio: %f", 
				this.camino, this.distancia, this.beneficio);
	}

}
