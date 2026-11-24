package com.gameplay.data.masterdata.template.unit.type.playable;

import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.trainer.instance.TrainerAlternate;
import com.gameplay.data.masterdata.template.unit.PartnerPokemon;
import com.gameplay.data.masterdata.template.unit.StarUnit;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewelEmblem;

public abstract class PlayableStarPartner extends StarUnit<TrainerAlternate, StarJewelEmblem> {

	protected PlayableStarPartner(Builder<?> builder) {
		super(builder);
	}

	public abstract static class Builder<B extends Builder<B>> extends StarUnit.Builder<B, TrainerAlternate, StarJewelEmblem> {
		protected Builder(String starPartnerId, String starPartnerCode, TrainerAlternate trainerAlternate) {
			super(starPartnerId, starPartnerCode, trainerAlternate);
		}

		protected Builder(String starPartnerId, String starPartnerCode, TrainerAlternate trainerAlternate, PartnerPokemon partnerPokemon) {
			super(starPartnerId, starPartnerCode, trainerAlternate, partnerPokemon);
		}

		@Override
		public abstract PlayableStarPartner build();
	}
}
