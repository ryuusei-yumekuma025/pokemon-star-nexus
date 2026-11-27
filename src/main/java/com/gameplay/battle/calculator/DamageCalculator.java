package com.gameplay.battle.calculator;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class DamageCalculator {

	public static final double STAB = 1.5;
	public static final int RANDOM_FACTOR = 85;

	private DamageCalculator() {}

	public static double getRandomFactor() {
		return (double) ThreadLocalRandom.current().nextInt(RANDOM_FACTOR, 100) / 100;
	}
}
