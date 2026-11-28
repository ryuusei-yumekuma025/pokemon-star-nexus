package com.gameplay.test.battle.entity.fieldeffect.entire;

import com.gameplay.data.masterdata.attribute.battle.field.entire.Terrain;
import com.gameplay.data.masterdata.attribute.battle.field.entire.Weather;

public class EntireBattleFieldEffect {

	private Weather currentWeather;
	private int remainingWeatherTurn;

	private Terrain currentTerrain;
	private int remainingTerrainTurn;

	private final RoomOnBattleField room;

	public EntireBattleFieldEffect() {
		this.currentWeather = Weather.NONE;
		this.remainingWeatherTurn = 0;

		this.currentTerrain = Terrain.NONE;
		this.remainingTerrainTurn = 0;

		this.room = new RoomOnBattleField();
	}

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

	public RoomOnBattleField getRoom() {
		return room;
	}

}
