package com.gameplay.masterdata.attribute.region;

import com.utils.StringUtils;

public enum WorldRegion {
	HOSHIN,
	KANTO,
	JOHTO,
	HOENN,
	SINNOH, HISUI,
	UNOVA,
	KALOS,
	ALOLA,
	GALAR,
	PALDEA;

	public static WorldRegion fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid WorldRegion : " + code);
		}
	}

	public static WorldRegion fromId(int id) {
		return switch (id) {
			case 0 -> HOSHIN;
			case 1 -> KANTO;
			case 2 -> JOHTO;
			case 3 -> HOENN;
			case 4 -> SINNOH;
			case 5 -> UNOVA;
			case 6 -> KALOS;
			case 7 -> ALOLA;
			case 8 -> GALAR;
			case 9 -> PALDEA;
			default -> null;
		};
	}
}
