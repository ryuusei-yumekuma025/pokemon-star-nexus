package com.gameplay.battle.calculator;

public final class ActionIntervalCalculator {

	public static final double BATTLE_ACT = 10000.0;
	public static final double MIN_ACTION_SPEED = 1.0;

	private ActionIntervalCalculator() {}

	public static double speedToActionSpeed(int speed) {
		double scaledSpeed = 100 * Math.pow((double) speed / 100, 0.3);
		return BATTLE_ACT / scaledSpeed;
	}
}
