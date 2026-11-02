package com.gameplay.masterdata.attribute.battle;

import com.utils.StringUtils;

public enum InterferenceCondition {
	CONFUSION,
	INFATUATION,
	FLINCH;

	public static InterferenceCondition fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid InterferenceCondition : " + code);
		}
	}
}
