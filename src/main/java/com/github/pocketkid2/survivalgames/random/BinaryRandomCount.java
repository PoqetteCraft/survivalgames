package com.github.pocketkid2.survivalgames.random;

import java.util.Random;

public class BinaryRandomCount {

	private Random random;

	public BinaryRandomCount() {
		random = new Random();
	}

	public BinaryRandomCount(long seed) {
		random = new Random(seed);
	}

	private boolean getBool() {
		return random.nextBoolean();
	}

	/**
	 * Using the Binary Count Algorithm, grab a new value ranging from 1...max
	 *
	 * The chance of a certain value X is 1 / 2^X
	 *
	 * @param max The maximum value allowed
	 * @return A value in the range 1...max
	 */
	public int getCount(int max) {
		return getCountInternal(1, max);
	}

	private int getCountInternal(int current, int max) {
		if (getBool() && (current < max)) {
			return getCountInternal(current + 1, max);
		} else {
			return current;
		}
	}

}
