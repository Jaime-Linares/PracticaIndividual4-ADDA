package ejercicio2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import _datos.DatosCursos;
import _soluciones.SolucionCursos;
import us.lsi.ag.BinaryData;

public class BinCursosAG implements BinaryData<SolucionCursos> {
	
	// Constructor
	public BinCursosAG(String fichero) {
		DatosCursos.iniDatos(fichero);
	}
	
	// Longitud que van a tener los cromosomas -> numero de cursos
	@Override
	public Integer size() {
		return DatosCursos.getNumCursos();
	}
	
	// Funcion fitness que mide lo bueno que es el cromosoma, para ello penalizaremos si
	// no se cogen todos los cursos y si se imparten en mas centros de los que se pueden impartir
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		Double coste = 0.;
		Double error = 0.;
		Set<Integer> tematicasEnCromosoma = new HashSet<>();
		Set<Integer> centrosEnCromosoma = new HashSet<>();
		
		// Calculamos el goal y añadimos las tematicas y centros a sus respectivos conjuntos
		for(int i=0; i<size(); i++) {
			if(ls.get(i) > 0) {
				coste += DatosCursos.getPrecioInscripcion(i);
				tematicasEnCromosoma.addAll(DatosCursos.getTematicasCurso(i));
				centrosEnCromosoma.add(DatosCursos.getCentroCurso(i));
			}
		}
		
		// Sumamos las penalizaciones
		if(tematicasEnCromosoma.size() < DatosCursos.getNumTematicas()) {
			error += DatosCursos.getNumTematicas() - tematicasEnCromosoma.size();
		}
		if(centrosEnCromosoma.size() > DatosCursos.getMaxCentros()) {
			error += centrosEnCromosoma.size() - DatosCursos.getMaxCentros();
		}
				
		return -coste - 1000*error*error;
	}

	// Funcion que se encarga de transformar una lista (cromosoma) en un objeto del tipo SolucionCursos
	@Override
	public SolucionCursos solucion(List<Integer> ls) {
		return SolucionCursos.of_Bin(ls);
	}

}
