package _soluciones;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosCafes;

public class SolucionCafes {
	
	// Propiedades
	private Double beneficio;
	private Map<String,Integer> solucion;
	
	// Metodo de factoria
	public static SolucionCafes of_Range(List<Integer> ls) {
		return new SolucionCafes(ls);
	}
	
	// Constructor
	private SolucionCafes(List<Integer> ls) {
		Double beneficioTotal = 0.;
		Map<String,Integer> mp = new HashMap<>();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i) > 0) {
				beneficioTotal += ls.get(i) * DatosCafes.getBeneficioVentaKg(i);
				mp.put(DatosCafes.getVariedades().get(i).nombre(), ls.get(i));
			}
		}
		
		beneficio = beneficioTotal;
		solucion = mp;
	}
		
	// Metodo toString que devuelve una cadena con los kg de cada variedad y el beneficio final obtenido
	@Override
	public String toString() {
		return String.format("Variedades = %s; Beneficio = %f", this.solucion, this.beneficio);
	}

}
