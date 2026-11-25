package com.gameplay.battle.entity.unit.stat;

import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.template.unit.BaseStat;

import java.util.ArrayList;
import java.util.List;

public class StatMultiplier {

	private final List<Double> statMultiplier = new ArrayList<>();

	public StatMultiplier(double hp, double attack, double defense, double specialAttack, double specialDefense, double speed) {
		this.statMultiplier.addFirst(hp);
		this.statMultiplier.add(1, attack);
		this.statMultiplier.add(2, defense);
		this.statMultiplier.add(3, specialAttack);
		this.statMultiplier.add(4, specialDefense);
		this.statMultiplier.add(5, speed);
	}

	public StatMultiplier() {
		this(0, 0, 0, 0, 0, 0);
	}

	public StatMultiplier(List<Double> statMultiplier) {
		this.statMultiplier.addAll(statMultiplier);
	}

	public List<Double> getStatMultiplierList() {
		return statMultiplier;
	}

	public double getStatMultiplier(StatType statType) {
		if (!statType.isBaseStat()) {
			return 0;
		}
		return statMultiplier.get(statType.ordinal());
	}

	public double getHPMultiplier() {
		return getStatMultiplier(StatType.HP);
	}

	public double getAttackMultiplier() {
		return getStatMultiplier(StatType.HP);
	}

	public double getDefenseMultiplier() {
		return getStatMultiplier(StatType.HP);
	}

	public double getSpecialAttackMultiplier() {
		return getStatMultiplier(StatType.HP);
	}

	public double getSpecialDefenseMultiplier() {
		return getStatMultiplier(StatType.HP);
	}

	public double getSpeedMultiplier() {
		return getStatMultiplier(StatType.HP);
	}

	public boolean addStatMultiplier(StatType statType, double addedStatMultiplier) {
		if (!statType.isBaseStat() || addedStatMultiplier <= 0) {
			return false;
		}
		double current = this.statMultiplier.get(statType.ordinal());
		this.statMultiplier.add(statType.ordinal(), current + addedStatMultiplier);
		return true;
	}

	public StatMultiplier copy() {
		return new StatMultiplier(this.statMultiplier);
	}
}
