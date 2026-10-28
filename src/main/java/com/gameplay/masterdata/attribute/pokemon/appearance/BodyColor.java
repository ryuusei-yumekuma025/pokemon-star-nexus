package com.gameplay.masterdata.attribute.pokemon.appearance;

import com.utils.StringUtils;

public enum BodyColor {
	RED,
	BLUE,
	YELLOW,
	GREEN,
	BLACK,
	BROWN,
	PURPLE,
	GRAY,
	WHITE,
	PINK;

	public static BodyColor fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid BodyColor : " + code);
		}
	}
}
