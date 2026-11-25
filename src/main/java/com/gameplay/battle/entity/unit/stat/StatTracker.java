package com.gameplay.battle.entity.unit.stat;

import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.template.unit.BaseStat;

public class StatTracker {

	private final BaseStat baseStat;
	private final FlatStat flatStat;
	private final StatMultiplier statMultiplier;

	public StatTracker(BaseStat baseStat, FlatStat flatStat, StatMultiplier statMultiplier) {
		this.baseStat = baseStat.copy();
		this.flatStat = flatStat.copy();
		this.statMultiplier = statMultiplier.copy();
	}

	public StatTracker(BaseStat baseStat) {
		this.baseStat = baseStat.copy();
		this.flatStat = new FlatStat();
		this.statMultiplier = new StatMultiplier();
	}

	public BaseStat getBaseStat() {
		return baseStat;
	}

	public FlatStat getFlatStat() {
		return flatStat;
	}

	public StatMultiplier getStatMultiplier() {
		return statMultiplier;
	}

	public int getStat(StatType statType, int level) {
		return (int) Math.round(baseStat.getBaseStat(statType) * (1 + statMultiplier.getStatMultiplier(statType)) +
				flatStat.getFlatStat(statType));
	}

	public StatTracker copy() {
		return new StatTracker(this.baseStat, this.flatStat, this.statMultiplier);
	}
}
