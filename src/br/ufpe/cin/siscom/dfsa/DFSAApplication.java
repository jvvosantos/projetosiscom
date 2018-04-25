package br.ufpe.cin.siscom.dfsa;

import java.io.IOException;

public class DFSAApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();

		Thread.sleep(90000);

		long endTime = System.currentTimeMillis() - startTime;

		System.out.print("Program finished running in: ");

		long hours = ((endTime / (1000 * 60 * 60)) % 24);
		long minutes = ((endTime / (1000 * 60)) % 60);
		long seconds = ((endTime / 1000) % 60);
		
		if (hours > 0) {			
			System.out.print(hours+" h");
		}
		if (minutes > 0) {			
			System.out.print(minutes+" min");
		}
		if (seconds > 0) {			
			System.out.print(seconds+" s");
		}
	}

}
