package ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosInvestigadores;
import _datos.Investigador;
import _datos.Trabajo;
import _soluciones.SolucionInvestigadores;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class InRangeInvestigadoresAG implements ValuesInRangeData<Integer, SolucionInvestigadores> {
	
	// Constructor
	public InRangeInvestigadoresAG(String fichero) {
		DatosInvestigadores.iniDatos(fichero);
	}

	// Tipo de cromosoma que vamos a usar -> cromosoma de rango
	public ChromosomeType type() {
		return ChromosomeType.Range;
	}

	// Longitud que va a tener el cromosoma -> num investigadores * num trabajos
	@Override
	public Integer size() {
		return DatosInvestigadores.getNumInvestigadores()*
				DatosInvestigadores.getNumTrabajos();
	}
	
	// Valor minimo que puede tomar un gen del cromosoma, es decir,
	// valor minimo de horas que un investigador le puede dedicar a un trabajo
	@Override
	public Integer min(Integer i) {
		return 0;
	}

	// Valor maximo que puede tomar un gen del cromosoma, es decir,
	// el valor maximo de dias que puede dedicarle un investigador
	// a un trabajo
	@Override
	public Integer max(Integer i) {
		List<Integer> res = new ArrayList<>();
		// Identificamos del investigador que se trata
		Integer numInvestigador = i/DatosInvestigadores.getNumTrabajos();
		Investigador inv = DatosInvestigadores.getInvestigadores().get(numInvestigador);
		// Identificamos el trabajo que se trata
		Integer numTrabajo = i%DatosInvestigadores.getNumTrabajos();
		Trabajo trabajo = DatosInvestigadores.getTrabajos().get(numTrabajo);
		// Obtenemos la especialidad del investigador y vemos cuantos dias son necesarias
		// para ese trabajo con aquella especialidad
		Integer especialidad = inv.especialidad();
		Integer diasNecesarios = trabajo.repartos().stream()
				.filter(x -> x.especialidad().equals(especialidad))
				.findFirst().get()
				.dias();
		// Añadimos tanto los dias disponibles del investigador como los diasNecesarios
		// a una lista y elegimos el menor de ambos, este sera el maximo que estamos buscando
		res.add(inv.capacidad());
		res.add(diasNecesarios);
		return res.stream()
				.min(Comparator.naturalOrder())
				.get() + 1;		// sumamos 1 porque es intervalo abierto
	}

	// Funcion fitness que sirve para medir lo bueno que es el cromosoma, para ello 
	// penalizaremos si nos pasamos de los dias disponibles de un investigador
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		Double goal = 0.;
		Double error = 0.;
		Map<Integer,Integer> diasPorInvestigador = new HashMap<>();
		
		Integer numTrabajos = DatosInvestigadores.getNumTrabajos();
		Integer numEspecialidades = DatosInvestigadores.getNumEspecialidades();
		
		// Para penalizar en caso de pasarnos de los dias de trabajo de cada investigador
		for(int i=0; i<size(); i++) {
			// Calculamos los dias que trabaja cada investigador segun el cromosoma
			Integer numInvestigador = i/numTrabajos;
			if(diasPorInvestigador.containsKey(numInvestigador)) {
				diasPorInvestigador.put(numInvestigador, diasPorInvestigador.get(numInvestigador)+ls.get(i));
			} else {
				diasPorInvestigador.put(numInvestigador, ls.get(i));
			}
		}
		// Miramos si cada investigador supera los dias maximos que puede trabajar y,
		// si es asi, penalizaremos los dias que se pase			
		for(Integer numInvestigador: diasPorInvestigador.keySet()) {
			Integer capacidad = DatosInvestigadores.getDiasDisponiblesInvestigador(numInvestigador);
			Integer diasTrabajados = diasPorInvestigador.get(numInvestigador);
			if(diasTrabajados > capacidad) {
				error += diasTrabajados-capacidad;
			}
		}
		
		// Sumamos al objetivo si se realiza el trabajo (se realiza si se cumple las horas necesarias
		// de cada especialidad)
		for(int j=0; j<numTrabajos; j++) {
			Boolean realiza = true;
			// Recorremos todas las especialidades dentro del trabajo
			for(int k=0; k<numEspecialidades; k++) {
				Integer diasPorEspecialidad = 0;
				// Recorremos el cromosoma y si ese investigador realiza ese trabajo y tiene la especialidad
				// apuntamos los dias para comprobar que se cumplen los dias necesarios, sino se cumple para 
				// una sola especialidad significa que el trabajo no se hace
				for(int i=0; i<size(); i++) {
					Integer numInvestigador = i/numTrabajos;
					Integer numTrabajo = i%numTrabajos;
					if(numTrabajo.equals(j) && 
							DatosInvestigadores.getInvestigadorTieneEspecialidad(numInvestigador, k).equals(1)) {
						diasPorEspecialidad += ls.get(i);
					}
				}
				if(diasPorEspecialidad != DatosInvestigadores.getDiasNecesariosTrabajosEspecialidad(j, k)) {
					realiza = false;
				}
			}
			// Si realiza el trabajo sumamos la calidad, sino pues a por el siguiente trabajo
			if(realiza) {
				goal += DatosInvestigadores.getCalidadTrabajo(j);
			}
		}
					
		return goal - 1000*error*error;
	}

	// Funcion que se encarga de transformar una lista (cromosoma) en un objeto del tipo SolucionInvestigadores
	@Override
	public SolucionInvestigadores solucion(List<Integer> ls) {
		return SolucionInvestigadores.of_Range(ls);
	}

}
