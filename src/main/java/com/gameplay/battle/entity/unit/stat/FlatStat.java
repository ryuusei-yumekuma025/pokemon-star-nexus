package com.gameplay.battle.entity.unit.stat;

import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.template.unit.BaseStat;

import java.util.ArrayList;
import java.util.List;

public class FlatStat {

	private final List<Integer> flatStat = new ArrayList<>();

	public FlatStat(int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
		this.flatStat.addFirst(hp);
		this.flatStat.add(1, attack);
		this.flatStat.add(2, defense);
		this.flatStat.add(3, specialAttack);
		this.flatStat.add(4, specialDefense);
		this.flatStat.add(5, speed);
	}

	public FlatStat() {
		this(0, 0, 0, 0, 0, 0);
	}

	public FlatStat(List<Integer> flatStat) {
		this.flatStat.addAll(flatStat);
	}

	public int getFlatStat(StatType statType) {
		if (!statType.isBaseStat()) {
			return 0;
		}
		return flatStat.get(statType.ordinal());
	}

	public int getFlatHP() {
		return getFlatStat(StatType.HP);
	}

	public int getFlatAttack() {
		return getFlatStat(StatType.ATTACK);
	}

	public int getFlatDefense() {
		return getFlatStat(StatType.DEFENSE);
	}

	public int getFlatSpecialAttack() {
		return getFlatStat(StatType.SPECIAL_ATTACK);
	}

	public int getFlatSpecialDefense() {
		return getFlatStat(StatType.SPECIAL_DEFENSE);
	}

	public int getFlatSpeed() {
		return getFlatStat(StatType.SPEED);
	}

	public boolean addFlatStat(StatType statType, int addedFlatStat) {
		if (!statType.isBaseStat() || addedFlatStat <= 0) {
			return false;
		}
		int current = this.flatStat.get(statType.ordinal());
		this.flatStat.add(statType.ordinal(), current + addedFlatStat);
		return true;
	}

	public FlatStat copy() {
		return new FlatStat(this.flatStat);
	}
}
