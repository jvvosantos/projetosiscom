package br.ufpe.cin.siscom.dfsa.estimator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.knowm.xchart.XYChart;

import br.ufpe.cin.siscom.dfsa.util.ChartUtils;

public class Q implements Estimator, Runnable {
	
	private int tags;
	private int simulations;
	private int increaseStep;
	
	private double empty;
	private double success;
	private double colision;
	
	private List<Double> tagsList = new ArrayList<>();
	
	private List<Double> numSlots = new ArrayList<>();
	private List<Double> numSlotsEmpty = new ArrayList<>();
	private List<Double> numSlotsColision = new ArrayList<>();
	
	public Q(int tags, int increasStep, int simulations) {
		this.tags = tags;
		this.simulations = simulations;
		this.increaseStep = increasStep;
	}
	

	@Override
	public int estimate(int success, int empty, int collision) {
		Random r = new Random(); 
		int arrayLimiter = 0, totalSlots = 0, i = 0, slot = 0;
		
		double Q = 4, Qfp = 4, Cq = 0.2, num =0;
		double frameEnd = Math.pow(2.0, Q);

		for(int z=tags; z<=simulations*increaseStep; z+=increaseStep){
			for(int k =0; k <2000; k++){
				totalSlots =0;
				int n = z;
				do {
					slot =0;
					arrayLimiter = (int) frameEnd;

					// Apply random tag inputs in the frame
					for (i = 0; i < n; i++) {
						int t = r.nextInt(arrayLimiter);
						//					System.out.println("random"+t);
						if(t==0)
							slot++;
					}
					switch(slot) {
					case 0:
						Qfp = Math.max(0, Qfp - Cq);
						this.empty++;
						break;
					case 1:
						n--;
						this.success++;
						break;
					default:
						Qfp = Math.min(15, Qfp + Cq);
						this.colision++;
						break;
					}

					Q = Math.round(Qfp);
					frameEnd = Math.pow(2.0, Q);
					totalSlots++;

				} while (n > 0);
				//				System.out.println(totalSlots);
				//				System.out.println((double)totalSlots/2000);
				num += (double)totalSlots/2000;
				this.numSlots.add((double) ((this.empty+this.success+this.colision)/2000.0));
				this.numSlotsEmpty.add((double) (this.empty/2000.0));
				this.numSlotsColision.add((double) (this.colision/2000.0));
				this.tagsList.add((double) z);
				this.empty = 0;
				this.colision = 0;
				this.success = 0;
//				System.out.println(totalSlots+" num : "+num);
			}
			System.out.println(z+" = "+num);
			num =0;
		
	}
		return 0;
	}

	@Override
	public String getName() {
		return "Q";
	}

	@Override
	public void run() {
		this.estimate(0, 0, 0);
		try {
			XYChart chartColision = ChartUtils.createChart("Number of Tags", "Colisions Slots", tags, 1900, this.tagsList.stream().mapToDouble(d -> d).toArray(), this.numSlotsColision.stream().mapToDouble(d -> d).toArray(), getName());
			XYChart chartEmpty = ChartUtils.createChart("Number of Tags", "Empty Slots", tags, 1200, this.tagsList.stream().mapToDouble(d -> d).toArray(), this.numSlotsEmpty.stream().mapToDouble(d -> d).toArray(), getName());
			XYChart chartSlots = ChartUtils.createChart("Number of Tags", "Number of Slots", tags, 3600, this.tagsList.stream().mapToDouble(d -> d).toArray(), this.numSlots.stream().mapToDouble(d -> d).toArray(), getName());
			List<XYChart> charts = Arrays.asList(chartColision, chartEmpty, chartSlots);
			ChartUtils.displayChartList(charts);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
