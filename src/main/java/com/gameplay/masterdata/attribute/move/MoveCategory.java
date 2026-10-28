package com.gameplay.masterdata.attribute.move;

import com.utils.StringUtils;

public enum MoveCategory {
	PHYSICAL, SPECIAL, STATUS;

	public static MoveCategory fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid MoveCategory : " + code);
		}
	}
}
