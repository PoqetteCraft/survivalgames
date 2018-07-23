package com.github.pocketkid2.survivalgames.random;

import java.util.Set;

import org.junit.Test;

public class RandomIntSetTest {

	@Test
	public void test() {
		final int SIZE = 100;

		RandomIntSet ris = new RandomIntSet(System.currentTimeMillis());

		for (int i = 0; i < SIZE; i++) {
			Set<Integer> ints = ris.getInts(10, 30);
			System.out.println(ints);
		}
	}

}
