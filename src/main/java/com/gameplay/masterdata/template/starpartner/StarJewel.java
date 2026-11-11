package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.attribute.starpartner.Starsoul;
import com.gameplay.masterdata.attribute.type.PokemonType;

public class StarJewel {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final PokemonType syncType;
	private final Starsoul starsoul;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private StarJewel(Builder builder) {
		this.syncType = builder.syncType;
		this.starsoul = builder.starsoul;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public PokemonType getSyncType() {
		return syncType;
	}

	public Starsoul getStarsoul() {
		return starsoul;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final PokemonType syncType;
		private final Starsoul starsoul;

		public Builder(String syncType, String starsoul) {
			this.syncType = PokemonType.fromCode(syncType);
			this.starsoul = Starsoul.fromCode(starsoul);
		}

		public StarJewel build() {
			return new StarJewel(this);
		}
	}
}
