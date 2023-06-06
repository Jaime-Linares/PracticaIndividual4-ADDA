package ejercicio1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosCafes;
import _datos.TuplaEj1;
import _soluciones.SolucionCafes;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class InRangeCafesAG implements ValuesInRangeData<Integer, SolucionCafes> {
	
	// Constructor
	public InRangeCafesAG(String fichero) {
		DatosCafes.iniDatos(fichero);
	}

	// Tipo de cromosoma que vamos a usar -> cromosoma de rango
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Range;
	}
	
	// Longitud que van a tener los cromosomas -> numero de variedades
	@Override
	public Integer size() {
		return DatosCafes.getNumVariedades();
	}
	
	// Valor minimo que puede tomar un gen del cromosoma, es decir,
	// valor minimo de kg que vamos a usar de una variedad
	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	// Valor maximo que puede tomar un gen del cromosoma, es decir, 
	// valor maximo de kg que vamos a usar de una variedad (que es el minimo del cociente
	// kgDisponibles/porcentajeCafePorKgVariedad de todos los componentes de la variedad)
	@Override
	public Integer max(Integer i) {
		List<Integer> res = new ArrayList<>();
		// obtenemos los cafes presentes en la variedad
		List<String> cafesEnVariedad = DatosCafes.getVariedades().get(i).comp().stream()
				.map(x -> x.nombreCafe())
				.toList();
		// obtenemos el cociente kgDisponibles/porcentajeCafePorKgVariedad
		for(int j=0; j<DatosCafes.getNumTiposCafe(); j++) {
			String nombreCafe = DatosCafes.getCafes().get(j).nombre();
			if(cafesEnVariedad.contains(nombreCafe)) {
				Integer kgDisponiblesCafe = DatosCafes.getCantidadCafe(j);
				Double porcentajeCafeEnVariedad = DatosCafes.getPorcentajeCafeKg(i, j);
				Integer numPosiblesKg = (int) (kgDisponiblesCafe/porcentajeCafeEnVariedad);
				res.add(numPosiblesKg);
			}
		}
		return res.stream()
				.min(Comparator.naturalOrder())
				.get() + 1;		// sumamos uno porque es intervalo abierto
	}

	// Funcion fitness que sirve para medir lo bueno que es el cromosoma, para ello 
	// penalizaremos si se pasa de los kgs disponibles de los cafes
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		Double goal = 0.;
		Double error = 0.;
		Map<String, Double> kgCafeEnCadaVariedad = new HashMap<>();
		
		// Calculamos los beneficios y ampliamos el map
		for(int i=0; i< size(); i++) {
			if(ls.get(i) > 0) {
				goal += DatosCafes.getBeneficioVentaKg(i) * ls.get(i);
				// Ampliamos el map con los kg que hemos gastado de cada cafe en las variedades elegidas
				for(TuplaEj1 tupla: DatosCafes.getVariedades().get(i).comp()) {
					String nombreCafe = tupla.nombreCafe();
					Double cantidadCafe = tupla.cantidadKg()*ls.get(i);
					if(kgCafeEnCadaVariedad.containsKey(nombreCafe)) {
						kgCafeEnCadaVariedad.put(nombreCafe, kgCafeEnCadaVariedad.get(nombreCafe)+cantidadCafe);
					} else {
						kgCafeEnCadaVariedad.put(nombreCafe, cantidadCafe);
					}
				}
			}			
		}
		
		// Sumamos al error lo que nos hallamos pasado de los kg disponibles de cada cafe
		for(int j=0; j<DatosCafes.getNumTiposCafe(); j++) {
			String nombre = DatosCafes.getCafes().get(j).nombre();
			Double kgDisponibles = DatosCafes.getCantidadCafe(j) + 0.;
			if(kgCafeEnCadaVariedad.containsKey(nombre) && (kgDisponibles<kgCafeEnCadaVariedad.get(nombre))) {
				error += kgCafeEnCadaVariedad.get(nombre) - kgDisponibles;
			}
		}
		
		return goal - 1000*error*error;
	}
	
	// Funcion que se encarga de transformar una lista (cromosoma) en un objeto del tipo SolucionCafes
	@Override
	public SolucionCafes solucion(List<Integer> ls) {
		return SolucionCafes.of_Range(ls);
	}

}
