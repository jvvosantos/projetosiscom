package br.ufpe.cin.siscom.dfsa.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;

import br.ufpe.cin.siscom.dfsa.domain.Result;
import br.ufpe.cin.siscom.dfsa.estimator.Estimator;
import br.ufpe.cin.siscom.dfsa.util.ChartUtils;
import br.ufpe.cin.siscom.dfsa.util.Logger;
import br.ufpe.cin.siscom.dfsa.util.RNG;

public class DFSASimulator implements Runnable {

	private int[] frame;
	private int frameSize;

	private int numTags;
	private int identifiedTags;
	private int increaseStep;

	private int simulations;

	private int colision;
	private int success;
	private int empty;

	private List<Result> results;

	private Estimator estimator;
	
	private List<Double> tags = new ArrayList<>(); 
	
	private List<Double> numSlots = new ArrayList<>();
	private List<Double> numSlotsEmpty = new ArrayList<>();
	private List<Double> numSlotsColision = new ArrayList<>();

	public DFSASimulator(int numTags, int increaseStep, int simulations, Estimator estimator) {
		this.frameSize = 64;
		this.numTags = numTags;
		this.increaseStep = increaseStep;
		this.simulations = simulations;
		this.results = new ArrayList<Result>();
		this.estimator = estimator;
		this.identifiedTags = 0;
		this.frame = new int[frameSize];
	}

	public void simulate() {
		int slot = 0;
		int colision = 0;
		int empty = 0;
		int success = 0;

		// Identification phase
		for (int runs = 0; runs < simulations; runs++) {
			for (int loops = 0; loops < 2000; loops++) {
				while(identifiedTags < numTags) {

					for (int i = numTags - identifiedTags; i > 0; i--) {
						slot = RNG.generateRandomInteger(frameSize);
						this.frame[slot]++;
					}

					// Check for status phase
					for (int i : frame) {
						if (i > 1) {
							colision++;
							this.colision++;
						}
						else if (i == 1) {
							success++;
							this.success++;
							identifiedTags++;
						}
						else {
							empty++;
							this.empty++;
						}
					}

					// Estimation phase
					if (!(identifiedTags == numTags)) {
						this.frameSize = estimator.estimate(success, empty, colision);
						success = 0;
						empty = 0;
						colision =0;
					}
					this.refresh();
				}
				identifiedTags = 0;
			}
			this.numSlots.add((double) ((this.empty+this.success+this.colision)/2000.0));
			this.numSlotsEmpty.add((double) (this.empty/2000.0));
			this.numSlotsColision.add((double) (this.colision/2000.0));
			this.tags.add((double) numTags);
			this.numTags += increaseStep;
			this.empty = 0;
			this.colision = 0;
			this.success = 0;
		}
	}


	public void generateResult(){
		results.add(new Result(colision, empty, success));
	}

	@Override
	public void run() {
		Logger.log("Starting simulation");

		long startTime = System.currentTimeMillis();

		this.simulate();
		
		try {
			XYChart chartColision = ChartUtils.createChart("Number of Tags", "Colisions Slots", numTags, 1900, this.tags.stream().mapToDouble(d -> d).toArray(), this.numSlotsColision.stream().mapToDouble(d -> d).toArray(), estimator.getName());
			XYChart chartEmpty = ChartUtils.createChart("Number of Tags", "Empty Slots", numTags, 1200, this.tags.stream().mapToDouble(d -> d).toArray(), this.numSlotsEmpty.stream().mapToDouble(d -> d).toArray(), estimator.getName());
			XYChart chartSlots = ChartUtils.createChart("Number of Tags", "Number of Slots", numTags, 3600, this.tags.stream().mapToDouble(d -> d).toArray(), this.numSlots.stream().mapToDouble(d -> d).toArray(), estimator.getName());
			List<XYChart> charts = Arrays.asList(chartColision, chartEmpty, chartSlots);
			ChartUtils.displayChartList(charts);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Logger.log(this.numSlotsEmpty);

		long endTime = System.currentTimeMillis() - startTime;

		Logger.logFinishTime("Simulation", endTime);
	}


	private void refresh(){
		this.frame = new int[frameSize];
	}
}
