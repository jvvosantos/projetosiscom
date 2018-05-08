package br.ufpe.cin.siscom.dfsa.thread;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<Result> results;
	
	public DFSASimulator(int frameSize, int numTags, int increaseStep, int simulations) {
		this.frameSize = frameSize;
		this.numTags = numTags;
		this.increaseStep = increaseStep;
		this.simulations = simulations;
		this.results = new ArrayList<Result>();
		this.refresh();
	}

	public void simulate() {
		int slot = 0;
		for (int i = numTags; i > 0; i--) {
			slot = RNG.generateRandomInteger(frameSize);
			this.frame[slot]++;
		}
	}

	public void checkForColisions() {
		for (int i : frame) {
			if (i > 1) {
				this.colision++;
			}
			else if (i == 1) {
				this.success++;
			}
			else {
				this.empty++;
			}
		}
	}
	
	public void generateResult(){
		results.add(new Result(colision, empty, success));
	}

	@Override
	public void run() {
		Logger.log("Starting simulation");
		
		long startTime = System.currentTimeMillis();
		for (int i = simulations; i > 0; i--) {
			this.simulate();
			this.checkForColisions();
			this.generateResult();
			this.refresh();
		}
		long endTime = System.currentTimeMillis() - startTime;
		
		Logger.log(this.results);
		Logger.log("Simulations ran: "+simulations);
		Logger.logFinishTime("Simulation", endTime);
	}

	
	private void refresh(){
		this.colision = 0;
		this.success = 0;
		this.empty = 0;
		this.frame = new int[frameSize];
	}
}
