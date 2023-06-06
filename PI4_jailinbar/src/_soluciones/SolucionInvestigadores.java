package _soluciones;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosInvestigadores;

public class SolucionInvestigadores {

	// Propiedades
	private Map<String, List<Integer>> solucion;
	private Integer goal;
		
	// Metodo de factoria
	public static SolucionInvestigadores of_Range(List<Integer> ls) {
		return new SolucionInvestigadores(ls);
	}
		
	// Constructor
	public SolucionInvestigadores(List<Integer> ls) {
		// Hallamos la suma de las calidades de los trabajos que se realizan
		Integer calidades = 0;
		for(int j=0; j<DatosInvestigadores.getNumTrabajos(); j++) {
			Boolean realiza = true;
			for(int k=0; k<DatosInvestigadores.getNumEspecialidades(); k++) {
				Integer diasPorEspecialidad = 0;
				for(int i=0; i<ls.size(); i++) {
					Integer numInvestigador = i/DatosInvestigadores.getNumTrabajos();
					Integer numTrabajo = i%DatosInvestigadores.getNumTrabajos();
					if(numTrabajo.equals(j) && 
							DatosInvestigadores.getInvestigadorTieneEspecialidad(numInvestigador, k).equals(1)) {
						diasPorEspecialidad += ls.get(i);
					}
				}
				if(diasPorEspecialidad != DatosInvestigadores.getDiasNecesariosTrabajosEspecialidad(j, k)) {
					realiza = false;
				}
			}
			if(realiza) {
				calidades += DatosInvestigadores.getCalidadTrabajo(j);
			}
		}
		
		// Hallamos las horas que cada investigador dedica a cada trabajo
		Map<String, List<Integer>> mp = new HashMap<>();
		for(int i=0; i<DatosInvestigadores.getNumInvestigadores(); i++) {
			// Dividimos el cromosoma por investigadores
			Integer inicio = i*DatosInvestigadores.getNumTrabajos();
			List<Integer> valoresInvestigador = ls.subList(inicio, inicio+DatosInvestigadores.getNumTrabajos());
			mp.put("INV"+(i+1), valoresInvestigador);
		}
		
		solucion = mp;
		goal = calidades;
	}
		
	// Metodo toString que devuelve una cadena con lo que cada investigador dedica a cada trabajo y la calidad obtenida
	@Override
	public String toString() {
		return String.format("Reparto obtenido = %s;\n- Suma de las calidades de los trabajos realizados = %d", 
				this.solucion, this.goal);
	}
	
}
