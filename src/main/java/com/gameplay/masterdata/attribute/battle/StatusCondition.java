package com.gameplay.masterdata.attribute.battle;

import com.utils.StringUtils;

public enum StatusCondition {
	NONE,
	BURN,
	PARALYSIS,
	FREEZE,
	POISON,
	BAD_POISON,
	SLEEP;

	public static StatusCondition fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid StatusCondition : " + code);
		}
	}
}
