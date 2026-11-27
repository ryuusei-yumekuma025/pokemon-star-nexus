package com.gameplay.battle.entity.unit.stat.modifier;

public class StatModifier {

	private final FlatStat flatStat;
	private final StatMultiplier statMultiplier;

	public StatModifier() {
		this.flatStat = new FlatStat();
		this.statMultiplier = new StatMultiplier();
	}

	public StatModifier(FlatStat flatStat, StatMultiplier statMultiplier) {
		this.flatStat = flatStat.copy();
		this.statMultiplier = statMultiplier.copy();
	}

	public FlatStat getFlatStat() {
		return flatStat;
	}

	public StatMultiplier getStatMultiplier() {
		return statMultiplier;
	}

	public StatModifier copy() {
		return new StatModifier(this.flatStat, this.statMultiplier);
	}
}
