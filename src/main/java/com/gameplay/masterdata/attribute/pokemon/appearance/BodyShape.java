package com.gameplay.masterdata.attribute.pokemon.appearance;

import com.utils.StringUtils;

public enum BodyShape {
	ROUND,
	LEGS,
	FISH,
	INSECT,
	FOUR_LEGGED,
	FOUR_WINGS,
	GATHERING,
	MULTI_LEG,
	UPRIGHT_TORSO,
	TWO_LEGS_AND_TAIL,
	TWO_LEGS_AND_HUMAN_FORM,
	TWO_WINGS,
	BELLY_CRAWLING_NO_LEGS,
	ARM;

	public static BodyShape fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid BodyShape : " + code);
		}
	}
}
