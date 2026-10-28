package com.gameplay.masterdata.attribute.trainer;

import com.utils.StringUtils;

public enum TrainerClass {
	TRAINER_ON_JOURNEY,
	RIVAL,
	GYM_LEADER,
	ELITE_FOUR,
	CHAMPION,
	FRONTIER_BRAIN;

	public static TrainerClass fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid TrainerClass : " + code);
		}
	}
}
