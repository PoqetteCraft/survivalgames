package com.github.pocketkid2.survivalgames.random;

import java.util.stream.IntStream;

import org.junit.Test;

public class BinaryRandomCountTest {

	@Test
	public void test() {
		final int SIZE = 100;

		int max = 1;
		double average;
		int[] results = new int[SIZE];
		BinaryRandomCount brc = new BinaryRandomCount(System.currentTimeMillis());
		for (int i = 0; i < SIZE; i++) {
			results[i] = brc.getCount(Integer.MAX_VALUE);
			if (results[i] > max) {
				max = results[i];
			}
			System.out.println("Binary count is: " + results[i]);
		}
		System.out.println("Max is " + max);
		average = (IntStream.of(results).sum()) / (double) SIZE;
		System.out.println("Avg is " + average);
		for (int i = 1; i <= max; i++) {
			int count = 0;
			for (int j = 0; j < SIZE; j++) {
				if (results[j] == i) {
					count++;
				}
			}
			System.out.println("Number of " + i + "'s: " + count);
		}
	}

}
