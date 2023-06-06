package ejemplo3;

import java.util.List;

import _datos.DatosAlumnos;
import _soluciones.SolucionAlumnos;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class InRangeAlumnosAG implements ValuesInRangeData<Integer, SolucionAlumnos>{

	@Override
	public Integer size() {
		return DatosAlumnos.getNumAlumnos();
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Range;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal=0.;
		Double k=0.;
		Integer r1=0;
		Integer r2=0;
		Integer r3=0;
		Integer m= DatosAlumnos.getNumGrupos();
		Integer n= DatosAlumnos.getNumAlumnos();
		
		//Goal
		for(int j=0;j<m;j++) {
			for(int i=0;i<n;i++) {
				
			}
		}
		
		//K(Maxima afinidad)
		k=5.*n;
		
		//r1
		for(int i=0;i<n;i++) {
			Boolean checkR1 =false;
			Integer sum=0;
			for(int j=0;j<m;j++) {
				sum+=value.get(j*m+1);
			}
			checkR1= (sum==1)?true:false;
			r1+= (checkR1)?0:1;
		}
		
		//r2
		for(int j=0;j<m;j++) {
			
			Boolean checkR2 =false;
			Integer sum=0;
			for(int i=0;i<n;i++) {
				sum+=value.get(j*m+1);
			}
			checkR2= (sum==DatosAlumnos.getTamGrupo())?true:false;
			r2+= (checkR2)?0:1;
		}
		
		//r3
		for(int i =0; i<n;i++) {
			Boolean checkR3 =false;
			Integer sum=0;
			for(int j=0;j<m;j++) {
				if(DatosAlumnos.getAfinidad(i, j)==0) {
					sum+=value.get(j*m+i);
				}
			}
			checkR3= (sum==0)?true:false;
			r3+= (checkR3)?0:1;
		}
		
		
		return goal -(r1*k)-(r2*k)-(r3*k);
	}

	@Override
	public SolucionAlumnos solucion(List<Integer> value) {
		return SolucionAlumnos.of(value);
	}

	@Override
	public Integer max(Integer i) {
		return DatosAlumnos.getNumAlumnos()+1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	

}
