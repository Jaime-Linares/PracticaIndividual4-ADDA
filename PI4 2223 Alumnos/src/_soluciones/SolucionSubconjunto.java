package _soluciones;

import java.util.List;
import java.util.stream.Collectors;

import _datos.DatosSubconjunto;
import _datos.DatosSubconjunto.Subconjunto;
import us.lsi.common.List2;

public class SolucionSubconjunto {
	
	public static SolucionSubconjunto create(List<Integer> ls) {
		return new SolucionSubconjunto(ls);
	}
	
	private Double total;
	private List<Subconjunto> subconjuntos;	
	
	private SolucionSubconjunto(List<Integer> ls) {   // en vd es to parecio al fitness
		total = 0.;
		subconjuntos = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				total += DatosSubconjunto.getPeso(i);
				subconjuntos.add(DatosSubconjunto.getSubConjunto(i));
			}
		}
	}

	@Override
	public String toString() {
		String s = subconjuntos.stream().map(e -> "S"+e.id())
		.collect(Collectors.joining(", ", "Subconjuntos elegidos: {", "}\n"));
		return String.format("%sCoste Total: %.1f", s, total);	
	}
}
