package com.gameplay.masterdata.attribute.type;

import com.utils.StringUtils;

public enum PokemonType {
	TYPELESS,
	NORMAL,
	FIGHTING,
	FLYING,
	POISON,
	GROUND,
	ROCK,
	BUG,
	GHOST,
	STEEL,
	FIRE,
	WATER,
	GRASS,
	ELECTRIC,
	PSYCHIC,
	ICE,
	DRAGON,
	DARK,
	FAIRY,
	STELLAR;

	public static PokemonType fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid PokemonType : " + code);
		}
	}

	public int getId() {
		if (this == STELLAR) {
			return 99;
		}

		return ordinal();
	}
}
