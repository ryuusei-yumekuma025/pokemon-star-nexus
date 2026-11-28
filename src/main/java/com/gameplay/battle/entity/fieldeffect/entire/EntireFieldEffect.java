package com.gameplay.battle.entity.fieldeffect.entire;

import com.gameplay.data.masterdata.attribute.battle.field.entire.Season;
import com.gameplay.data.masterdata.attribute.battle.field.entire.Terrain;
import com.gameplay.data.masterdata.attribute.battle.field.entire.Weather;

public class EntireFieldEffect {

	/*------------------------------------------------------------------------------------------------------------------
	.	Fields																											*/

	private Weather currentWeather;
	private int remainingWeatherTurn;

	private Terrain currentTerrain;
	private int remainingTerrainTurn;

	private Season currentSeason;
	private int remainingSeasonTurn;

	/*------------------------------------------------------------------------------------------------------------------
	.	Constructor																										*/

	public EntireFieldEffect() {
		this.currentWeather = Weather.NONE;
		this.remainingWeatherTurn = 0;

		this.currentTerrain = Terrain.NONE;
		this.remainingTerrainTurn = 0;

		this.currentSeason = Season.NONE;
		this.remainingSeasonTurn = 0;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Weather																											*/

	public Weather getCurrentWeather() {
		return currentWeather;
	}

	public int getRemainingWeatherTurn() {
		return remainingWeatherTurn;
	}

	public boolean applyWeatherFieldEffect(Weather weather, int duration) {
		if (weather == null || weather == Weather.NONE || duration <= 0) {
			return false;
		}

		if (this.currentWeather.isSuper() && !weather.isSuper()) {
			if (!Weather.equalsWeather(this.currentWeather, weather)) {
				return false;
			}

			this.remainingWeatherTurn += Math.max(1, duration * 2 / 3);
			return true;
		}

		this.currentWeather = weather;
		this.remainingWeatherTurn = duration;

		return true;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Terrain																											*/

	public Terrain getCurrentTerrain() {
		return currentTerrain;
	}

	public int getRemainingTerrainTurn() {
		return remainingTerrainTurn;
	}

	public boolean applyTerrainFieldEffect(Terrain terrain, int duration) {
		if (terrain == null || terrain == Terrain.NONE || duration <= 0) {
			return false;
		}

		if (this.currentTerrain.isSuper() && !terrain.isSuper()) {
			if (!Terrain.equalsTerrain(this.currentTerrain, terrain)) {
				return false;
			}

			this.remainingTerrainTurn += Math.max(1, duration * 2 / 3);
			return true;
		}

		this.currentTerrain = terrain;
		this.remainingTerrainTurn = duration;

		return true;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Season																											*/

	public Season getCurrentSeason() {
		return currentSeason;
	}

	public int getRemainingSeasonTurn() {
		return remainingSeasonTurn;
	}

	public boolean applySeasonFieldEffect(Season season, int duration) {
		if (season == null || season == Season.NONE || duration <= 0) {
			return false;
		}

		if (this.currentSeason.isSuper() && !season.isSuper()) {
			if (!Season.equalsSeason(this.currentSeason, season)) {
				return false;
			}

			this.remainingSeasonTurn += Math.max(1, duration * 2 / 3);
			return true;
		}

		this.currentSeason = season;
		this.remainingSeasonTurn = duration;

		return true;
	}
}
