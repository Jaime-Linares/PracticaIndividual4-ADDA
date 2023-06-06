package ejercicio2;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import _datos.Curso;
import _datos.DatosCursos;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio2PLE {
	
	public static Integer maxCentros;
	public static List<Curso> cursos;
		
	
	// Funcion que devuelve el numero de cursos
	public static Integer getNumCursos() {
		return cursos.size();
	}
	
	// Funcion que devuelve el numero de tematicas
	public static Integer getNumTematicas() {
		Set<Integer> tematicas = new HashSet<>();
		for(Curso c: cursos) {
			Set<Integer> t = c.tematicas();
			tematicas.addAll(t);
		}
		return tematicas.size();
	}
	
	// Funcion que devuelve el numero de centros
	public static Integer getNumCentros() {
		Set<Integer> centros = new HashSet<>();
		for(Curso c: cursos) {
			Integer centro = c.centro();
			centros.add(centro);
		}
		return centros.size();
	}
	
	// Funcion que devuelve el numero maximo de centros
	public static Integer getMaxCentros() {
		return maxCentros;
	}
	
	// Funcion que devuelve un 1 si el curso i trata la tematica j, sino un 0
	public static Integer getCursoTieneTematica(Integer i, Integer j) {
		return cursos.get(i).tematicas().contains(j)? 1:0; 
	}
	
	// Funcion que devuelve el precio de la inscripcion del curso i
	public static Double getPrecioInscripcion(Integer i) {
		return cursos.get(i).coste();
	}
	
	// Funcion que devuelve un 1 si el curso i se imparte en el centro k, sino un 0
	public static Integer getCursoEnCentro(Integer i, Integer k) {
		return cursos.get(i).centro().equals(k)? 1:0;
	}
	
	
	// Función que calcula la solucion utilizando gurobi
	public static void ejercicio2_model() throws IOException {
		DatosCursos.iniDatos("ficheros/Ejercicio2DatosEntrada1.txt");
		
		maxCentros = DatosCursos.maxCentros;
		cursos = DatosCursos.cursos;
		
		AuxGrammar.generate(Ejercicio2PLE.class, "lsi_models/Ejercicio2.lsi", "gurobi_models/Ejercicio2-1.lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio2-1.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}


	// TEST
	public static void main(String[] args) throws IOException {
		System.out.println("* TEST Ejercicio2PLE *\n");
		ejercicio2_model();
	}

}
