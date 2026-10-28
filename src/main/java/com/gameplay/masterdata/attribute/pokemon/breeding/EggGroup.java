package com.gameplay.masterdata.attribute.pokemon.breeding;

import com.utils.StringUtils;

public enum EggGroup {
	NO_EGGS_DISCOVERED,
	MONSTER,
	WATER_1,
	BUG,
	FLYING,
	FIELD,
	FAIRY,
	GRASS,
	HUMAN_LIKE,
	WATER_3,
	MINERAL,
	AMORPHOUS,
	WATER_2,
	DITTO,
	DRAGON;

	public static EggGroup fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid EggGroup : " + code);
		}
	}
}
