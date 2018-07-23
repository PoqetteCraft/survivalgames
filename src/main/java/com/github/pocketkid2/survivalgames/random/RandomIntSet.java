package com.github.pocketkid2.survivalgames.random;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomIntSet {

	private Random random;

	public RandomIntSet() {
		random = new Random();
	}

	public RandomIntSet(long seed) {
		random = new Random(seed);
	}

	/**
	 * Returns a set of random integers in the range 0...max-1
	 *
	 * Max MUST BE greater than count, or this function will break
	 *
	 *
	 * @param count The number of ints to grab
	 * @param max   The maximum value for an int
	 * @return
	 */
	public Set<Integer> getInts(int count, int max) {
		assert (count < max);
		Set<Integer> ints = new HashSet<Integer>();
		for (int i = 0; i < count; i++) {
			int val;
			do {
				val = random.nextInt(max);
			} while (ints.contains(val));
			ints.add(val);
		}
		return ints;
	}

}
