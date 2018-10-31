package br.ufpe.cin.siscom.dfsa.util;

import java.util.Random;

public class RNG {

	private static Random rngesus = new Random();

	public static int generateRandomInteger(int max) {
		System.out.println("penes");
		return rngesus.nextInt(max);
	}

}
