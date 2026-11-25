package com.gameplay.data.masterdata.attribute.stat;

public enum StatType {
	HP,
	ATTACK,
	DEFENSE,
	SPECIAL_ATTACK,
	SPECIAL_DEFENSE,
	SPEED,
	ACCURACY,
	EVASIVENESS;

	public boolean canModify() {
		return this != HP;
	}

	public boolean isBaseStat() {
		return this != ACCURACY && this != EVASIVENESS;
	}
}
