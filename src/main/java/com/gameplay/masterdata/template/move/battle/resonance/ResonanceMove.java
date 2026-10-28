package com.gameplay.masterdata.template.move.battle.resonance;

import com.gameplay.masterdata.data.master.registry.move.battle.PokemonMoveRegistry;
import com.gameplay.masterdata.data.master.registry.starpartner.StarPartnerRegistry;
import com.gameplay.masterdata.template.move.battle.BattleMove;
import com.gameplay.masterdata.template.move.battle.pokemon.PokemonMove;
import com.gameplay.masterdata.template.starpartner.StarPartner;

public class ResonanceMove extends BattleMove {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final String starPartnerId;
	private final PokemonMove movePrototype;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private ResonanceMove(Builder builder) {
		super(builder);
		this.starPartnerId = builder.starPartnerId;
		this.movePrototype = builder.movePrototype;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	@Override
	public int getMoveIdNo() {
		return Integer.parseInt(getMoveId().substring(getMoveId().length() - 3));
	}

	public StarPartner getStarPartner() {
		return StarPartnerRegistry.getStarPartnerById(starPartnerId);
	}

	public PokemonMove getMovePrototype() {
		return movePrototype;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder extends BattleMove.Builder<Builder> {
		private final String starPartnerId;
		private final PokemonMove movePrototype;

		public Builder(String starPartnerId, int moveId, String moveCode, String movePrototype) {
			PokemonMove prototype = PokemonMoveRegistry.getPokemonMoveByCode(movePrototype);
			String prototypeId = String.format("%08d", prototype.getMoveIdNo());
			super("RM_" + starPartnerId + prototypeId + String.format("%03d", moveId), moveCode, prototype);
			this.starPartnerId = starPartnerId;
			this.movePrototype = prototype;
		}

		@Override
		public ResonanceMove build() {
			return new ResonanceMove(this);
		}
	}
}
