package com.gameplay.masterdata.attribute.pokemon.gender;

import com.utils.StringUtils;

public enum PokemonGender {
	MALE,
	FEMALE,
	GENDERLESS;

	public static PokemonGender fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid PokemonGender : " + code);
		}
	}

	public static PokemonGender fromId(int id) {
		return switch (id) {
			case 1 -> MALE;
			case 2 -> FEMALE;
			case 0 -> GENDERLESS;
			default -> null;
		};
	}
}
