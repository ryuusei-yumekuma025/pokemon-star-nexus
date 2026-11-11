package com.gameplay.masterdata.attribute.battle.field.entire;

import com.utils.StringUtils;

public enum Season {
	NONE,
	SPRING,
	SUMMER,
	AUTUMN,
	WINTER,
	SAKURA_DREAMY_SPRING,
	SUNFLOWER_BUZZING_SUMMER,
	LOTUS_RUSTLING_AUTUMN,
	CAMELLIA_FROSTY_WINTER;

	public static Season fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid Season : " + code);
		}
	}

	public boolean isSuper() {
		return switch (this) {
			case SAKURA_DREAMY_SPRING, SUNFLOWER_BUZZING_SUMMER, LOTUS_RUSTLING_AUTUMN, CAMELLIA_FROSTY_WINTER -> true;
			default -> false;
		};
	}

	public int getSeasonTypeId() {
		return switch (this) {
			case SPRING, SAKURA_DREAMY_SPRING -> 1;
			case SUMMER, SUNFLOWER_BUZZING_SUMMER -> 2;
			case AUTUMN, LOTUS_RUSTLING_AUTUMN -> 3;
			case WINTER, CAMELLIA_FROSTY_WINTER -> 4;
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
