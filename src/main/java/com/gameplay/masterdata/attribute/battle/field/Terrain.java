package com.gameplay.masterdata.attribute.battle.field;

public enum Terrain {
	NONE,
	GRASSY_TERRAIN,
	OVERGROWN_GRASSY_TERRAIN,
	ELECTRIC_TERRAIN,
	OVERCHARGED_ELECTRIC_TERRAIN,
	PSYCHIC_TERRAIN,
	OVERWARPED_PSYCHIC_TERRAIN,
	MISTY_TERRAIN;

	public boolean isSuper() {
		return switch (this) {
			case OVERGROWN_GRASSY_TERRAIN, OVERCHARGED_ELECTRIC_TERRAIN, OVERWARPED_PSYCHIC_TERRAIN -> true;
			default -> false;
		};
	}

	public int getTerrainTypeId() {
		return switch (this) {
			case GRASSY_TERRAIN, OVERGROWN_GRASSY_TERRAIN -> 1;
			case ELECTRIC_TERRAIN, OVERCHARGED_ELECTRIC_TERRAIN -> 2;
			case PSYCHIC_TERRAIN, OVERWARPED_PSYCHIC_TERRAIN -> 3;
			case MISTY_TERRAIN -> 4;
			default -> 0;
		};
	}

	public static boolean equalsTerrain(Terrain firstTerrain, Terrain secondTerrain) {
		if (firstTerrain == null || secondTerrain == null) {
			return false;
		}

		if (firstTerrain == secondTerrain) {
			return true;
		}

		return firstTerrain.getTerrainTypeId() == secondTerrain.getTerrainTypeId();
	}
}
