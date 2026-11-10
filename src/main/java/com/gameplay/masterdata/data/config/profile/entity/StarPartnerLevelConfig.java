package com.gameplay.masterdata.data.config.profile.entity;

public final class StarPartnerLevelConfig {

	public static final int[] LEVEL_CAP_BY_EVOLUTION_RANK = {50, 80, 100, 120, 140, 150};
	public static final int MAX_NON_EX_EVOLUTION_RANK = LEVEL_CAP_BY_EVOLUTION_RANK.length - 1;
	public static final int MAX_EVOLUTION_RANK = MAX_NON_EX_EVOLUTION_RANK + 50;

	private StarPartnerLevelConfig() {}

	public static int getLevelCap(int evolutionRank) {
		if (evolutionRank < 0 || evolutionRank > MAX_EVOLUTION_RANK) {
			return -1;
		}

		if (evolutionRank < MAX_NON_EX_EVOLUTION_RANK) {
			return LEVEL_CAP_BY_EVOLUTION_RANK[evolutionRank];
		}

		return LEVEL_CAP_BY_EVOLUTION_RANK[MAX_NON_EX_EVOLUTION_RANK] + evolutionRank - MAX_NON_EX_EVOLUTION_RANK;
	}
}
