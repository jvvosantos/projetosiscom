package br.ufpe.cin.siscom.dfsa;

import java.io.IOException;

import br.ufpe.cin.siscom.dfsa.estimator.EomLee;
import br.ufpe.cin.siscom.dfsa.estimator.LowerBound;
import br.ufpe.cin.siscom.dfsa.estimator.Q;
import br.ufpe.cin.siscom.dfsa.thread.DFSASimulator;
import br.ufpe.cin.siscom.dfsa.util.ChartUtils;
import br.ufpe.cin.siscom.dfsa.util.Logger;

public class DFSAApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		(new Thread(new DFSASimulator(100, 100, 10, new EomLee()))).start();;
	}

}
