package com.gameplay.data.masterdata.template.unit;

import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.data.config.LevelConfig;

import java.util.ArrayList;
import java.util.List;

public class BaseStat {

	private final List<Integer> baseStatAtLevel150 = new ArrayList<>();
	private final List<Integer> nexusEvolutionBonus = new ArrayList<>();
	private final List<Double> statGrowthLevelLowPoint = new ArrayList<>();
	private final List<Double> statGrowthLevelHighPoint = new ArrayList<>();

	public BaseStat(Builder builder) {
		this.baseStatAtLevel150.addAll(builder.baseStatAtLevel150);
		this.nexusEvolutionBonus.addAll(builder.nexusEvolutionBonus);
		this.statGrowthLevelLowPoint.addAll(builder.statGrowthLevelLowPoint);
		this.statGrowthLevelHighPoint.addAll(builder.statGrowthLevelHighPoint);
	}

	public int getBaseStat(StatType statType, int level, int nexusEvolutionRank) {
		if (!statType.isBaseStat() || level > LevelConfig.getLevelCap(nexusEvolutionRank) || level <= 0) {
			return 0;
		}

		int bonusRank = Math.min(nexusEvolutionRank, LevelConfig.MAX_NON_EX_EVOLUTION_RANK);

		if (level >= 150) {
			double stat =  (double) baseStatAtLevel150.get(statType.ordinal()) +
					(level - 150) * statGrowthLevelHighPoint.get(statType.ordinal()) +
					bonusRank * nexusEvolutionBonus.get(statType.ordinal());
			return (int) Math.round(stat);
		}

		double growthStep = (statGrowthLevelHighPoint.get(statType.ordinal()) - statGrowthLevelLowPoint.get(statType.ordinal()))
				/ LevelConfig.STAT_GROWTH_STEP;

		int step = LevelConfig.getCurrentStep(level);

		double finalStat = baseStatAtLevel150.get(statType.ordinal()) + bonusRank * nexusEvolutionBonus.get(statType.ordinal());

		for (int currentStep = 6; currentStep > step; currentStep--) {
			double growth = statGrowthLevelLowPoint.get(statType.ordinal()) + currentStep * growthStep;

			int range = LevelConfig.STAT_GROWTH_BREAKPOINT[currentStep + 1] - LevelConfig.STAT_GROWTH_BREAKPOINT[currentStep];

			finalStat -= range * growth;
		}

		finalStat -= (LevelConfig.STAT_GROWTH_BREAKPOINT[step + 1] - level) *
				(statGrowthLevelLowPoint.get(statType.ordinal()) + step * growthStep);

		return (int) Math.round(finalStat);
	}

	public int getBaseHP(int level, int nexusEvolutionRank) {
		return getBaseStat(StatType.HP, level, nexusEvolutionRank);
	}

	public int getBaseAttack(int level, int nexusEvolutionRank) {
		return getBaseStat(StatType.ATTACK, level, nexusEvolutionRank);
	}

	public int getBaseDefense(int level, int nexusEvolutionRank) {
		return getBaseStat(StatType.DEFENSE, level, nexusEvolutionRank);
	}

	public int getBaseSpecialAttack(int level, int nexusEvolutionRank) {
		return getBaseStat(StatType.SPECIAL_ATTACK, level, nexusEvolutionRank);
	}

	public int getBaseSpecialDefense(int level, int nexusEvolutionRank) {
		return getBaseStat(StatType.SPECIAL_DEFENSE, level, nexusEvolutionRank);
	}

	public int getBaseSpeed(int level, int nexusEvolutionRank) {
		return getBaseStat(StatType.SPEED, level, nexusEvolutionRank);
	}

	public BaseStat copy() {
		Builder builder = new Builder()
				.setBaseStatAtLevel150(this.baseStatAtLevel150)
				.setNexusEvolutionBonus(this.nexusEvolutionBonus)
				.setStatGrowthLevelLowPoint(this.statGrowthLevelLowPoint)
				.setStatGrowthLevelHighPoint(this.statGrowthLevelHighPoint);

		return builder.build();
	}

	public static class Builder {
		private final List<Integer> baseStatAtLevel150 = new ArrayList<>();
		private final List<Integer> nexusEvolutionBonus = new ArrayList<>();
		private final List<Double> statGrowthLevelLowPoint = new ArrayList<>();
		private final List<Double> statGrowthLevelHighPoint = new ArrayList<>();

		public Builder setBaseStatAtLevel150(List<Integer> baseStatAtLevel100) {
			this.baseStatAtLevel150.clear();
			this.baseStatAtLevel150.addAll(baseStatAtLevel100);
			return this;
		}

		public Builder setNexusEvolutionBonus(List<Integer> nexusEvolutionBonus) {
			this.nexusEvolutionBonus.clear();
			this.nexusEvolutionBonus.addAll(nexusEvolutionBonus);
			return this;
		}

		public Builder setStatGrowthLevelLowPoint(List<Double> statGrowthLevelLowPoint) {
			this.statGrowthLevelLowPoint.clear();
			this.statGrowthLevelLowPoint.addAll(statGrowthLevelLowPoint);
			return this;
		}

		public Builder setStatGrowthLevelHighPoint(List<Double> statGrowthLevelHighPoint) {
			this.statGrowthLevelHighPoint.clear();
			this.statGrowthLevelHighPoint.addAll(statGrowthLevelHighPoint);
			return this;
		}

		public BaseStat build() {
			return new BaseStat(this);
		}
	}
}
