package _datos;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosMulticonjunto {
	
	public static int SUMA;	
	private static List<Integer> numeros; 
	
	// LECTURA DE FICHERO
	public static void iniDatos(String fichero) {
		String[] v = Files2.linesFromFile(fichero).get(0).split(":");
		SUMA = Integer.parseInt(v[1].trim());
		numeros = List2.parse(v[0], ",", Integer::parseInt);	
		toConsole();
	}

	// Métodos que es probable que no saquemos de primeras, sino que necesitamo hacer el LSI antes
	public static Integer getSuma() {
		return SUMA;
	}
	public static Integer getNumElementos() {
		return numeros.size();
	}
	public static Integer getElemento(Integer i) {
		return numeros.get(i);
	}
	public static Integer getMultiplicidad(Integer i){
		return SUMA / numeros.get(i);
	}
	public static List<Integer> getListaNumeros(){
		return numeros;
	}
	
	// Para enseñar los datos por pantalla
	public static void toConsole() {
		String2.toConsole("Conjunto de Entrada: %s\nSuma objetivo: %d", numeros, SUMA);	
	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/Ejemplo1DatosEntrada1.txt");
	}	
}
