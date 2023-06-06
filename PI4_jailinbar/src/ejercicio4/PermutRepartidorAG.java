package ejercicio4;

import java.util.List;

import _datos.DatosRepartidor;
import _soluciones.SolucionRepartidor;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class PermutRepartidorAG implements SeqNormalData<SolucionRepartidor>{
	
	// Constructor
	public PermutRepartidorAG(String fichero) {
		DatosRepartidor.iniDatos(fichero);
	}
		
	// Tipo de cromosoma que vamos a usar -> cromosoma de permutacion
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
		
	// Longitud que van a tener los cromosomas -> numero de vertices del grafo
	@Override
	public Integer itemsNumber() {
		return DatosRepartidor.getNumVertices();
	}
	
	// Funcion fitness que sirve para medir lo bueno que es el cromosoma, para ello
	// penalizaremos si no existe la arista. Establecemos el Cliente con id 0 como inicial
	// y el ultimo tiene que ser el 0 si no tambien penalizamos
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		Double goal = 0.;
		Double km = 0.;
		Double error = 0.;
		
		for(int i=0; i<size(); i++) {
			// Beneficio += beneficio - cada minuto que se pase (ponemos los km, ya que tarda 1 minuto en hacer 1km)
			// Si no existe la arista aumentamos el error
			if(i==0) {
				if(DatosRepartidor.existeArista(0, ls.get(i))) {
					km += DatosRepartidor.getPesoArista(0, ls.get(i));
					goal += DatosRepartidor.getBeneficioCliente(ls.get(i)) - km;
				} else {
					error++;
				}
			} else {
				if(DatosRepartidor.existeArista(ls.get(i-1), ls.get(i))) {
					km += DatosRepartidor.getPesoArista(ls.get(i-1), ls.get(i));
					goal += DatosRepartidor.getBeneficioCliente(ls.get(i)) - km;
				} else {
					error++;
				}
			}
		}
		// Si el ultimo vertice no es el cliente 0, es decir, el inicial, aumentamos el error
		if(!ls.get(ls.size()-1).equals(0)) {
			error++;
		}
		
		return goal - 10000*error*error;
	}

	// Funcion que se encarga de transformar una lista (cromosoma) en un objeto del tipo SolucionRepartidor 
	@Override
	public SolucionRepartidor solucion(List<Integer> ls) {
		return SolucionRepartidor.of_Permut(ls);
	}

}
