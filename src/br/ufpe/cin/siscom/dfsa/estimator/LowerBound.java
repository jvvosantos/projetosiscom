package br.ufpe.cin.siscom.dfsa.estimator;

public class LowerBound implements Estimator {

	@Override
	public int estimate(int success, int empty, int collision) {
		return collision*2;
	}

	@Override
	public String getName() {
		return "Lower-Bound";
	}

}
