package com.gameplay.masterdata.attribute.battle.field.side;

import com.gameplay.masterdata.attribute.region.WorldRegion;

public enum RegionRealm {
	RED_KANTO,
	BLUE_KANTO,
	GREEN_KANTO,
	GOLD_JOHTO,
	SILVER_JOHTO,
	CRYSTAL_JOHTO,
	RUBY_HOENN,
	SAPPHIRE_HOENN,
	EMERALD_HOENN,
	DIAMOND_SINNOH,
	PEARL_SINNOH,
	PLATINUM_SINNOH,
	BLACK_UNOVA,
	WHITE_UNOVA,
	GRAY_UNOVA,
	X_KALOS,
	Y_KALOS,
	Z_KALOS,
	SUN_ALOLA,
	MOON_ALOLA,
	LIGHT_ALOLA,
	SWORD_GALAR,
	SHIELD_GALAR,
	ETERNAL_GALAR,
	SCARLET_PALDEA,
	VIOLET_PALDEA,
	INDIGO_PALDEA;

	public int getRealmCategory() {
		return switch (this) {
			case RED_KANTO,
				 GOLD_JOHTO,
				 RUBY_HOENN,
				 DIAMOND_SINNOH,
				 BLACK_UNOVA,
				 X_KALOS,
				 SUN_ALOLA,
				 SWORD_GALAR,
				 SCARLET_PALDEA
			-> 1;
			case BLUE_KANTO,
				 SILVER_JOHTO,
				 SAPPHIRE_HOENN,
				 PEARL_SINNOH,
				 WHITE_UNOVA,
				 Y_KALOS,
				 MOON_ALOLA,
				 SHIELD_GALAR,
				 VIOLET_PALDEA
			-> 2;
			case GREEN_KANTO,
				 CRYSTAL_JOHTO,
				 EMERALD_HOENN,
				 PLATINUM_SINNOH,
				 GRAY_UNOVA,
				 Z_KALOS,
				 LIGHT_ALOLA,
				 ETERNAL_GALAR,
				 INDIGO_PALDEA
			-> 3;
		};
	}

	public WorldRegion getRegion() {
		return WorldRegion.fromCode(this.toString().substring(this.toString().indexOf('_') + 1));
	}
}
