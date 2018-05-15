package br.ufpe.cin.siscom.dfsa.estimator;

public interface Estimator {
	
	int estimate(int success, int empty, int collision);
	
	String getName();

}
