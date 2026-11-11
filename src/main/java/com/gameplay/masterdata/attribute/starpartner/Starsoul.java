package com.gameplay.masterdata.attribute.starpartner;

import com.utils.StringUtils;

public enum Starsoul {
	PASSION,
	DESIRE,
	VOLITION,
	EMOTION,
	MEMORY,
	REASON;

	public static Starsoul fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid Starsoul : " + code);
		}
	}
}
