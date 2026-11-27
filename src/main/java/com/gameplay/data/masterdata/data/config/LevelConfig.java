package com.gameplay.data.masterdata.data.config;

public final class LevelConfig {

	public static final int[] LEVEL_CAP_BY_EVOLUTION_RANK = {50, 80, 100, 120, 140, 150};
	public static final int MAX_NON_EX_EVOLUTION_RANK = LEVEL_CAP_BY_EVOLUTION_RANK.length - 1;
	public static final int MAX_EVOLUTION_RANK = MAX_NON_EX_EVOLUTION_RANK + 50;

	public static final int[] STAT_GROWTH_BREAKPOINT = {1, 30, 45, 75, 100, 120, 140, 150};
	public static final int STAT_GROWTH_STEP = STAT_GROWTH_BREAKPOINT.length - 1;

	private LevelConfig() {}

	public static int getLevelCap(int evolutionRank) {
		if (evolutionRank < 0 || evolutionRank > MAX_EVOLUTION_RANK) {
			return -1;
		}

		if (evolutionRank < MAX_NON_EX_EVOLUTION_RANK) {
			return LEVEL_CAP_BY_EVOLUTION_RANK[evolutionRank];
		}

		return LEVEL_CAP_BY_EVOLUTION_RANK[MAX_NON_EX_EVOLUTION_RANK] + evolutionRank - MAX_NON_EX_EVOLUTION_RANK;
	}

	public static int getCurrentStep(int level) {
		if (level <= 0) {
			return -1;
		}

		if (level == 1) {
			return 0;
		}

		for (int currentStep = 0; currentStep < STAT_GROWTH_STEP; ++currentStep) {
			if (STAT_GROWTH_BREAKPOINT[currentStep] < level && STAT_GROWTH_BREAKPOINT[currentStep + 1] >= level) {
				return currentStep;
			}
		}

		return STAT_GROWTH_STEP;
	}
}
