package com.gameplay.masterdata.attribute.battle.field;

public enum Weather {
	NONE,
	HARSH_SUNLIGHT,
	EXTREMELY_HARSH_SUNLIGHT,
	RAIN,
	HEAVY_RAIN,
	SANDSTORM;

	public boolean isSuper() {
		return switch (this) {
			case EXTREMELY_HARSH_SUNLIGHT, HEAVY_RAIN -> true;
			default -> false;
		};
	}
}
