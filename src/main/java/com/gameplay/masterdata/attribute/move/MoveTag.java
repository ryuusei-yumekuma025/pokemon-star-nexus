package com.gameplay.masterdata.attribute.move;

import com.utils.StringUtils;

public enum MoveTag {
	CONTACT,
	UNPROTECTABLE,
	PROTECT_BREAK,

	BALL_AND_BOMB,
	BITING,
	DANCE,
	EXPLOSIVE,
	POWDER_AND_SPORE,
	PULSE,
	PUNCHING,
	SLICING,
	SOUND_BASED,
	WIND,

	DEFROST,
	DRAINING,
	HEALING,
	MULTISTRIKE,
	ONE_HIT_KNOCKOUT,
	RECOIL,
	SURE_HIT;

	public static MoveTag fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid MoveTag : " + code);
		}
	}
}
