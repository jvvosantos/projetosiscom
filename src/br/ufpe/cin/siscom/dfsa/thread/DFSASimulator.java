package br.ufpe.cin.siscom.dfsa.thread;

import br.ufpe.cin.siscom.dfsa.domain.Result;
import br.ufpe.cin.siscom.dfsa.util.Logger;
import br.ufpe.cin.siscom.dfsa.util.RNG;

public class DFSASimulator implements Runnable {

	private int[] frame;
	private int frameSize;

	private int numTags;
	private int increaseStep;

	private int simulations;

	private int colision;
	private int success;
	private int empty;

	public DFSASimulator(int frameSize, int numTags, int increaseStep, int simulations) {
		this.frameSize = frameSize;
		this.frame = new int[frameSize];
		this.numTags = numTags;
		this.increaseStep = increaseStep;
		this.simulations = simulations;
		this.colision = 0;
	}

	public void simulate() {
		for (int i = 0; i < frame.length; i++) {
			this.frame[i] = RNG.generateRandomInteger(numTags);
		}
	}

	public void checkForColisions() {
		for (int i : frame) {
			if (i == 1) {
				this.success++;
			}
			else if (i == 0) {
				this.empty++;
			}
			else {
				this.colision++;
			}
		}
	}

	@Override
	public void run() {
		Logger.log("Starting simulation");
		
		long startTime = System.currentTimeMillis();
		for (int i = simulations; i > 0; i--) {
			this.simulate();
			this.checkForColisions();
		}
		Result result = new Result(colision, empty, success);
		long endTime = System.currentTimeMillis() - startTime;
		
		Logger.log(result);
		Logger.log("Simulations ran: "+simulations);
		Logger.logFinishTime("Simulation", endTime);
	}

}
