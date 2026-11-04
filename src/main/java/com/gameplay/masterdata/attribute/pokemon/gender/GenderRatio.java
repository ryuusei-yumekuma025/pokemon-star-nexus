package com.gameplay.masterdata.attribute.pokemon.gender;

import com.utils.StringUtils;

public enum GenderRatio {
	MALE_ONLY,
	MALE_7,
	MALE_3,
	BALANCE,
	FEMALE_3,
	FEMALE_7,
	FEMALE_ONLY,
	GENDERLESS;

	public static GenderRatio fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		if (StringUtils.isNullOrBlank(fixedCode)) {
			throw new IllegalArgumentException("Invalid GenderRatio : " + code);
		}

		return switch (fixedCode) {
			case "1_0", "MALE_ONLY" -> MALE_ONLY;
			case "7_1", "MALE_7" -> MALE_7;
			case "3_1", "MALE_3" -> MALE_3;
			case "1_1", "BALANCE" -> BALANCE;
			case "1_3", "FEMALE_3" -> FEMALE_3;
			case "1_7", "FEMALE_7" -> FEMALE_7;
			case "0_1", "FEMALE_ONLY" -> FEMALE_ONLY;
			case "0_0", "GENDERLESS" -> GENDERLESS;
			default -> throw new IllegalArgumentException("Invalid GenderRatio : " + code);
		};
	}

	public double getMaleRate() {
		return switch (this) {
			case MALE_ONLY -> 100.0;
			case MALE_7 -> 87.5;
			case MALE_3 -> 75.0;
			case BALANCE -> 50.0;
			case FEMALE_3 -> 25.0;
			case FEMALE_7 -> 12.5;
			case FEMALE_ONLY -> 0.0;
			case GENDERLESS -> -1.0;
		};
	}

	public double getRate(PokemonGender gender) {
		return switch (gender) {
			case MALE -> getMaleRate();
			case FEMALE -> {
				if (this == GENDERLESS) yield -1.0;
				yield 100.0 - getMaleRate();
			}
			case GENDERLESS -> -1.0;
		};
	}
}
