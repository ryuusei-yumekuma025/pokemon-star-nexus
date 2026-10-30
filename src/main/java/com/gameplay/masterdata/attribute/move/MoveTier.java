package com.gameplay.masterdata.attribute.move;

import com.utils.StringUtils;

public enum MoveTier {
	LOW, MID, HIGH, SUPER;

	public static MoveTier fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid MoveTier : " + code);
		}
	}

	public static MoveTier fromId(int id) {
		return switch (id) {
			case 1 -> LOW;
			case 2 -> MID;
			case 3 -> HIGH;
			case 4 -> SUPER;
			default -> throw new IllegalArgumentException("Invalid MoveTier Id : " + id);
		};
	}
}
