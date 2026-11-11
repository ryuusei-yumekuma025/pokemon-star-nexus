package com.gameplay.masterdata.attribute.battle.field.entire;

import com.utils.StringUtils;

public enum Room {
	TRICK_ROOM,
	WONDER_ROOM,
	MAGIC_ROOM;

	public static Room fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid Room : " + code);
		}
	}
}
