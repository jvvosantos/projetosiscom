package br.ufpe.cin.siscom.dfsa.thread;

public class Chen implements Estimador{
	
	public static double fatorial(double auxA, double auxB, double auxC, double auxD){
		double resultado = 1;
		
		while(auxA > 1){
			resultado = resultado * auxA;
			auxA = auxA - 1;
			
			if(auxB > 1){
				resultado = resultado / auxB;
				auxB = auxB - 1;
			}
			
			if(auxC > 1){
				resultado = resultado / auxC;
				auxC = auxC - 1;
			}
			
			if(auxD > 1){
				resultado = resultado / auxD;
				auxD = auxD - 1;
			}
		}
		
		return resultado;
	}
	
	public static double pow(double auxA, double auxB){
		double resultado = auxA;
		
		for(int i = 0; i < auxB; i++){
			resultado *= auxA;
		}
		
		return resultado;
	}

	@Override
	public int estimador(int sucess, int empty, int collision) {
		double size = sucess + empty + collision;
		if(size == 0){
			return 64;
		}
		
		double auxN = sucess + 2.0 * collision;
		double proximo = 0.0;
		double prever = -1.0;
		
		double auxR, auxJ, auxJR;
		
		while(prever < proximo){
			auxR = Math.pow(1.0 - (1.0/size), auxN);
			auxJ = (auxN/size) * Math.pow(1.0 - (1.0/size), auxN - 1);
			auxJR = 1.0 - auxR - auxJ;
			
			prever = proximo;
			
			proximo = fatorial(size, empty, sucess, collision) * Math.pow(auxR, sucess)*Math.pow(auxJ, sucess)*Math.pow(auxJR, collision);
			
			auxN = auxN + 1;
		}
		return (int) Math.ceil(auxN - 2);
	}
	
	/*
	 * Testa Algoritmo*/
	
//	public static void main(String []args){
//		Chen chen = new Chen();
//		
//		System.out.println(chen.estimador(3, 5, 2));
//	}

}
