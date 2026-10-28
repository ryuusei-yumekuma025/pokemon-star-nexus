package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.attribute.type.PokemonType;

public class StarJewel {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final PokemonType syncType;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private StarJewel(Builder builder) {
		this.syncType = builder.syncType;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public PokemonType getSyncType() {
		return syncType;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final PokemonType syncType;

		public Builder(String syncType) {
			this.syncType = PokemonType.fromCode(syncType);
		}

		public StarJewel build() {
			return new StarJewel(this);
		}
	}
}
