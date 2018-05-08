package br.ufpe.cin.siscom.dfsa;

import java.io.IOException;

import br.ufpe.cin.siscom.dfsa.thread.DFSASimulator;
import br.ufpe.cin.siscom.dfsa.util.ChartUtils;
import br.ufpe.cin.siscom.dfsa.util.Logger;

public class DFSAApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();
		ChartUtils.displayChart(ChartUtils.createChart("Numero de Slots", "Numero de tags", 1000, 3500, null));
		long endTime = System.currentTimeMillis() - startTime;
		Logger.logFinishTime("Program", endTime);
	}

}
