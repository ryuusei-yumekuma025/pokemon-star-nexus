package com.gameplay.masterdata.attribute.battle.field;

public enum Weather {
	NONE,
	HARSH_SUNLIGHT,
	EXTREMELY_HARSH_SUNLIGHT,
	RAIN,
	HEAVY_RAIN,
	SANDSTORM;

	public boolean isSuper() {
		return switch (this) {
			case EXTREMELY_HARSH_SUNLIGHT, HEAVY_RAIN -> true;
			default -> false;
		};
	}

	public int getWeatherTypeId() {
		return switch (this) {
			case HARSH_SUNLIGHT, EXTREMELY_HARSH_SUNLIGHT -> 1;
			case RAIN, HEAVY_RAIN -> 2;
			case SANDSTORM -> 3;
			default -> 0;
		};
	}

	public static boolean equalsWeather(Weather firstWeather, Weather secondWeather) {
		if (firstWeather == null || secondWeather == null) {
			return false;
		}

		if (firstWeather == secondWeather) {
			return true;
		}

		return firstWeather.getWeatherTypeId() == secondWeather.getWeatherTypeId();
	}
}
