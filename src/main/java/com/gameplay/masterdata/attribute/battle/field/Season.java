package com.gameplay.masterdata.attribute.battle.field;

public enum Season {
	NONE,
	SPRING,
	SUMMER,
	AUTUMN,
	WINTER,
	DREAMY_SPRING,
	BUZZING_SUMMER,
	RUSTLING_AUTUMN,
	FROSTY_WINTER;

	public boolean isSuper() {
		return switch (this) {
			case DREAMY_SPRING, BUZZING_SUMMER, RUSTLING_AUTUMN, FROSTY_WINTER -> true;
			default -> false;
		};
	}

	public int getSeasonTypeId() {
		return switch (this) {
			case SPRING, DREAMY_SPRING -> 1;
			case SUMMER, BUZZING_SUMMER -> 2;
			case AUTUMN, RUSTLING_AUTUMN -> 3;
			case WINTER, FROSTY_WINTER -> 4;
			default -> 0;
		};
	}

	public static boolean equalsSeason(Season firstSeason, Season secondSeason) {
		if (firstSeason == null || secondSeason == null) {
			return false;
		}

		if (firstSeason == secondSeason) {
			return true;
		}

		return firstSeason.getSeasonTypeId() == secondSeason.getSeasonTypeId();
	}
}
