package br.ufpe.cin.siscom.dfsa;

import java.io.IOException;

import br.ufpe.cin.siscom.dfsa.thread.DFSASimulator;
import br.ufpe.cin.siscom.dfsa.util.Logger;

public class DFSAApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();
		DFSASimulator simulator = new DFSASimulator(64, 100, 100, 2000);
		Thread t = new Thread(simulator);
		t.start();
		t.join();
		long endTime = System.currentTimeMillis() - startTime;
		Logger.logFinishTime("Program", endTime);
	}

}
