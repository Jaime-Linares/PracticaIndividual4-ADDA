package _soluciones;

import java.util.ArrayList;
import java.util.List;

import _datos.DatosCursos;

public class SolucionCursos {
	
	// Propiedades
	private Double coste;
	private List<String> cursos;
	
	// Metodo de factoria
	public static SolucionCursos of_Bin(List<Integer> ls) {
		return new SolucionCursos(ls);
	}
	
	// Constructor
	public SolucionCursos(List<Integer> ls) {
		Double costeTotal = 0.;
		List<String> cursosElegidos = new ArrayList<>();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i) > 0) {
				costeTotal += DatosCursos.getPrecioInscripcion(i);
				String curso = DatosCursos.getCursos().get(i).id();
				cursosElegidos.add(curso);
			}
		}
		
		coste = costeTotal;
		cursos = cursosElegidos;
	}

	// Metodo toString que devuelve una cadena con los cursos y el coste total de elegir esos cursos
	@Override
	public String toString() {
		return String.format("Cursos seleccionados = %s; Coste = %f", this.cursos, this.coste);
	}

}
