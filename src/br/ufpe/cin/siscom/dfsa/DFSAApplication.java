package br.ufpe.cin.siscom.dfsa;

import java.io.IOException;

import br.ufpe.cin.siscom.dfsa.estimator.EomLee;
import br.ufpe.cin.siscom.dfsa.thread.DFSASimulator;

public class DFSAApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		(new Thread(new DFSASimulator(100, 100, 1000, new EomLee()))).start();;
	}

}
