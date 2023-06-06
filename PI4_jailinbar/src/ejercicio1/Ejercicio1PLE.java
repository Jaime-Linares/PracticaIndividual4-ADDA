package ejercicio1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import _datos.Cafe;
import _datos.DatosCafes;
import _datos.Variedad;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio1PLE {

	public static List<Cafe> cafes;
	public static List<Variedad> variedades;
	
	
	// Funcion que calcula el numero de tipo de cafe
	public static Integer getNumTiposCafe() {
		return cafes.size();
	}

	// Funcion que calcula el numero de variedades
	public static Integer getNumVariedades() {
		return variedades.size();
	}

	// Funcion que calcula los kg disponibles de cafe del tipo j
	public static Integer getCantidadCafe(Integer j) {
		return cafes.get(j).kgDisponibles();
	}

	// Funcion que calcula el beneficio de venta por cada kg de la variedad i
	public static Double getBeneficioVentaKg(Integer i) {
		return variedades.get(i).beneficio();
	}

	// Funcion que calcula el porcentaje del cafe j necesarios para un kg de la variedad i
	public static Double getPorcentajeCafeKg(Integer i, Integer j) {
		String nombreCafe = cafes.get(j).nombre();
		return variedades.get(i).comp().stream()
				.filter(x -> x.nombreCafe().equals(nombreCafe))
				.map(x -> x.cantidadKg())
				.findFirst()
				.orElse(0.);
	}
	
	
	// Función que calcula la solucion utilizando gurobi
	public static void ejercicio1_model() throws IOException {
		DatosCafes.iniDatos("ficheros/Ejercicio1DatosEntrada1.txt");

		cafes = DatosCafes.getCafes();
		variedades = DatosCafes.getVariedades();
		
		AuxGrammar.generate(Ejercicio1PLE.class, "lsi_models/Ejercicio1.lsi", "gurobi_models/Ejercicio1-1.lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio1-1.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
		
	
	// TEST
	public static void main(String[] args) throws IOException {
		System.out.println("* TEST Ejercicio1PLE *\n");
		ejercicio1_model();
	}

}
