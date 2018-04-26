package br.ufpe.cin.siscom.dfsa.domain;

public class Result {

	private int colision;
	private int empty;
	private int success;

	private double colisionPercent;
	private double emptyPercent;
	private double successPercent;

	public Result(int colision, int empty, int success) {
		double total = colision + empty + success;
		this.colision = colision;
		this.empty = empty;
		this.success = success;

		this.colisionPercent = (colision/total)*100;
		this.emptyPercent = (empty/total)*100;
		this.successPercent = (success/total)*100;

	}

	@Override
	public String toString() {
		String reeturn = "Result {\n"
				+ "\tColisions: "+colision+" ("+colisionPercent+"%)\n"
				+ "\tSuccess Slots: "+success+" ("+successPercent+"%)\n"
				+ "\tEmpty Slots: "+empty+" ("+emptyPercent+"%)\n"
				+ "}";
		return reeturn;
	}

	/*
	 *  General getters and setters
	 */

	public int getColision() {
		return colision;
	}

	public void setColision(int colision) {
		this.colision = colision;
	}

	public int getEmpty() {
		return empty;
	}

	public void setEmpty(int empty) {
		this.empty = empty;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public double getColisionPercent() {
		return colisionPercent;
	}

	public void setColisionPercent(double colisionPercent) {
		this.colisionPercent = colisionPercent;
	}

	public double getEmptyPercent() {
		return emptyPercent;
	}

	public void setEmptyPercent(double emptyPercent) {
		this.emptyPercent = emptyPercent;
	}

	public double getSuccessPercent() {
		return successPercent;
	}

	public void setSuccessPercent(double successPercent) {
		this.successPercent = successPercent;
	}

}
