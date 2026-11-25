package com.gameplay.data.masterdata.template.unit;

import com.gameplay.data.masterdata.attribute.stat.StatType;

import java.util.ArrayList;
import java.util.List;

public class BaseStat {

	private final List<Integer> baseStat = new ArrayList<>();

	public BaseStat(int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
		this.baseStat.addFirst(hp);
		this.baseStat.add(1, attack);
		this.baseStat.add(2, defense);
		this.baseStat.add(3, specialAttack);
		this.baseStat.add(4, specialDefense);
		this.baseStat.add(5, speed);
	}

	public BaseStat(List<Integer> baseStat) {
		this.baseStat.addAll(baseStat);
	}

	public int getBaseStat(StatType statType) {
		if (!statType.isBaseStat()) {
			return 0;
		}
		return baseStat.get(statType.ordinal());
	}

	public int getBaseHP() {
		return getBaseStat(StatType.HP);
	}

	public int getBaseAttack() {
		return getBaseStat(StatType.ATTACK);
	}

	public int getBaseDefense() {
		return getBaseStat(StatType.DEFENSE);
	}

	public int getBaseSpecialAttack() {
		return getBaseStat(StatType.SPECIAL_ATTACK);
	}

	public int getBaseSpecialDefense() {
		return getBaseStat(StatType.SPECIAL_DEFENSE);
	}

	public int getBaseSpeed() {
		return getBaseStat(StatType.SPEED);
	}

	public boolean addBaseStat(StatType statType, int addedBaseStat) {
		if (!statType.isBaseStat() || addedBaseStat <= 0) {
			return false;
		}
		int current = this.baseStat.get(statType.ordinal());
		this.baseStat.add(statType.ordinal(), current + addedBaseStat);
		return true;
	}

	public BaseStat copy() {
		return new BaseStat(this.baseStat);
	}
}
