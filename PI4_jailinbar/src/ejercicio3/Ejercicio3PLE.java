package ejercicio3;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import _datos.DatosInvestigadores;
import _datos.Investigador;
import _datos.Trabajo;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio3PLE {
	
	public static List<Investigador> investigadores;
	public static List<Trabajo> trabajos;


	// Funcion que me devuelve el numero de investigadores
	public static Integer getNumInvestigadores() {
		return investigadores.size();
	}

	// Funcion que me devuelve el numero de especialidades
	public static Integer getNumEspecialidades() {
		Set<Integer> especialidades = new HashSet<>();
		for(Investigador inv: investigadores) {
			Integer especialidad = inv.especialidad();
			especialidades.add(especialidad);
		}
		return especialidades.size();
	}

	// Funcion que me devuelve el numero de trabajos
	public static Integer getNumTrabajos() {
		return trabajos.size();
	}

	// Funcion que me devuelve un 1 si el investigador i tiene la especialidad k, sino devuelve un 0
	public static Integer getInvestigadorTieneEspecialidad(Integer i, Integer k) {
		return investigadores.get(i).especialidad().equals(k)? 1:0;
	}

	// Funcion que me devuelve los dias disponibles del investigador i
	public static Integer getDiasDisponiblesInvestigador(Integer i) {
		return investigadores.get(i).capacidad();
	}

	// Funcion que me devuelve los dias necesarios para el trabajo j del investigador con especialidad k
	public static Integer getDiasNecesariosTrabajosEspecialidad(Integer j, Integer k) {
		return trabajos.get(j).repartos().stream()
				.filter(x -> x.especialidad().equals(k))
				.findFirst()
				.get()
				.dias();
	}

	// Funcion que me devuelve la calidad del trabajo j
	public static Integer getCalidadTrabajo(Integer j) {
		return trabajos.get(j).calidad();
	}


	// Función que calcula la solucion utilizando gurobi
	public static void ejercicio3_model() throws IOException {
		DatosInvestigadores.iniDatos("ficheros/Ejercicio3DatosEntrada1.txt");

		investigadores = DatosInvestigadores.getInvestigadores();
		trabajos = DatosInvestigadores.getTrabajos();

		AuxGrammar.generate(Ejercicio3PLE.class, "lsi_models/Ejercicio3.lsi", "gurobi_models/Ejercicio3-1.lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio3-1.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}


	// TEST
	public static void main(String[] args) throws IOException {
		System.out.println("* TEST Ejercicio3PLE *\n");
		ejercicio3_model();
	}

}
