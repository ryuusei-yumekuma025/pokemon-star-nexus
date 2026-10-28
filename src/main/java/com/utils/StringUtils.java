package com.utils;

import java.util.Locale;

public final class StringUtils {
	public static boolean isNullOrBlank(String s) {
		return s == null || s.isBlank();
	}

	public static boolean isNullOrBlank(String... str) {
		if (str == null) {
			return true;
		}

		for (String s : str) {
			if (isNullOrBlank(s)) {
				return true;
			}
		}

		return false;
	}

	public static String normalize(String s) {
		if (s == null) {
			return null;
		}

		return s.replaceAll("[\\p{Punct}&&[^_]]", " ")
				.trim()
				.replaceAll("[\\s-]+", "_");
	}

	public static String upperSnakeCase(String s) {
		if (isNullOrBlank(s)) {
			return null;
		}

		return normalize(s).toUpperCase(Locale.ROOT);
	}

	public static String lowerSnakeCase(String s) {
		if (isNullOrBlank(s)) {
			return null;
		}

		return normalize(s).toLowerCase(Locale.ROOT);
	}
}
