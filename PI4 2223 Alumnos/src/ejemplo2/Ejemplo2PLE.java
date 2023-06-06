package ejemplo2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import _datos.DatosSubconjunto;
import _datos.DatosSubconjunto.Subconjunto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejemplo2PLE {
	// MUY PARECIDO A EJERCICIO 2
 /*Variables:
  * 	una por subconjunto -> variables binarias (los coges o no)
  * F.objetivo:
  * 	minimizar la suma de los pesos -> Sum[wi * xi] desde i = 0 hasta n - 1
  * Restricciones:
  * 	Sum[contieneElemento(i,j)*xi >= 1] desde j = 0 hasta m - 1 
  * */
	
	private static List<Integer> universo;
	private static List<Subconjunto> subconjuntos;
	
	public static Integer getNumSubconjuntos() {
		return subconjuntos.size();
	}
	public static Integer getNumElementos() {
		return universo.size();
	}
	public static Double getPeso(Integer i) {
		return subconjuntos.get(i).peso();
	}
	public static Integer contieneElemento(Integer i, Integer j) {
		return subconjuntos.get(i).elementos().contains(universo.get(j)) ? 1: 0;
	}
	
	public static void ejemplo2_model() throws IOException{
		DatosSubconjunto.iniDatos("ficheros/Ejemplo2DatosEntrada1.txt");
		
		subconjuntos = DatosSubconjunto.getSubconjuntos();
		universo = DatosSubconjunto.getUniverso();
		
		AuxGrammar.generate(Ejemplo2PLE.class, "lsi_models/Ejemplo2.lsi",
				"gurobi_models/Ejemplo2-1.lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejemplo2-1.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d) -> d > 0.));
	}
	
	public static void main(String[] args) throws IOException{
		ejemplo2_model();
	}
}
