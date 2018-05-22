package br.ufpe.cin.siscom.dfsa.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.XYChart;

import br.ufpe.cin.siscom.dfsa.estimator.Estimator;
import br.ufpe.cin.siscom.dfsa.util.ChartUtils;
import br.ufpe.cin.siscom.dfsa.util.Logger;
import br.ufpe.cin.siscom.dfsa.util.RNG;

public class DFSASimulator implements Runnable {

	private int frameSize;
	private int loops;

	private int numTags;
	private int increaseStep;
	private int maxNumTags;

	private Estimator estimator;


	// Resultados
	private List<Double> tags = new ArrayList<>(); 
	private List<Double> numSlots = new ArrayList<>();
	private List<Double> numSlotsEmpty = new ArrayList<>();
	private List<Double> numSlotsColision = new ArrayList<>();
	private List<Double> identificationTime = new ArrayList<>();
	private List<Double> eficiency = new ArrayList<>();

	public DFSASimulator(int numTags, int increaseStep, int maxNumTags, Estimator estimator) {
		this.frameSize = 64;
		this.loops = 2000;
		this.numTags = numTags;
		this.increaseStep = increaseStep;
		this.maxNumTags = maxNumTags;
		this.estimator = estimator;
	}

	public void simulate() {
		for (; numTags <= maxNumTags; numTags+=increaseStep) {
			int totalSlots = 0;
			int totalCollision = 0;
			int totalEmpty = 0;
			int totalSuccess = 0;
			long totalTime = 0;

			for (int loops = 0; loops < this.loops; loops++) {
				int success;
				int colision;
				int empty;
				int chosenSlot;
				int	frameSize = this.frameSize;
				int	tagsRemaining = numTags;

				long startTime = System.currentTimeMillis();
				while(tagsRemaining > 0) {
					success = 0;
					colision = 0;
					empty = 0;

					int[] frame = new int[frameSize];

					for (int i = tagsRemaining; i > 0; i--) {
						chosenSlot = RNG.generateRandomInteger(frameSize);
						frame[chosenSlot]++;
					}

					for (int i : frame) {
						totalSlots++;
						if (i > 1) {
							totalCollision++;
							colision++;
						}
						else if (i == 1) {
							totalSuccess++;
							success++;
						}
						else {
							totalEmpty++;
							empty++;
						}
					}

					tagsRemaining -= success;

					frameSize = estimator.estimate(success, empty, colision);
				}
				long endTime = System.currentTimeMillis() - startTime;
				totalTime += endTime;
			}
			this.numSlots.add((double) (totalSlots/this.loops));
			this.numSlotsEmpty.add((double) (totalEmpty/this.loops));
			this.numSlotsColision.add((double) (totalCollision/this.loops));
			this.identificationTime.add((double) totalTime);
			this.eficiency.add((totalSuccess/(double)totalSlots)*100);
			this.tags.add((double) numTags);
		}
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
			XYChart chartTime = ChartUtils.createChart("Number of Tags", "Avg. Identification Time", numTags, 500, this.tags.stream().mapToDouble(d -> d).toArray(), this.identificationTime.stream().mapToDouble(d -> d).toArray(), estimator.getName());
			XYChart chartEficiency = ChartUtils.createChart("Number of Tags", "Eficiency", numTags, 40, this.tags.stream().mapToDouble(d -> d).toArray(), this.eficiency.stream().mapToDouble(d -> d).toArray(), estimator.getName());

			List<XYChart> charts = Arrays.asList(chartColision, chartEmpty, chartSlots, chartEficiency);
			
			ChartUtils.displayChartList(charts);
			ChartUtils.displayChart(chartTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis() - startTime;

		Logger.logFinishTime("Simulation", endTime);
	}
}
