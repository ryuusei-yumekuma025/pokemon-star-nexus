package com.gameplay.masterdata.template.move.starnexus;

import com.gameplay.masterdata.attribute.move.MoveCategory;
import com.gameplay.masterdata.attribute.type.PokemonType;
import com.gameplay.masterdata.data.master.registry.move.battle.PokemonMoveRegistry;
import com.gameplay.masterdata.data.master.registry.starpartner.StarPartnerRegistry;
import com.gameplay.masterdata.template.move.Move;
import com.gameplay.masterdata.template.move.battle.pokemon.PokemonMove;
import com.gameplay.masterdata.template.move.starnexus.starmode.mode.ConstellationMode;
import com.gameplay.masterdata.template.move.starnexus.starmode.mode.NebulaMode;
import com.gameplay.masterdata.template.starpartner.StarPartner;

public class StarNexusMove extends Move {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final String starPartnerId;
	private final PokemonMove movePrototype;
	private final PokemonType moveType;
	private final MoveCategory moveCategory;
	private final NebulaMode nebulaMode;
	private final ConstellationMode constellationMode;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private StarNexusMove(Builder builder) {
		super(builder);
		this.starPartnerId = builder.starPartnerId;
		this.movePrototype = builder.movePrototype;
		this.moveType = builder.moveType;
		this.moveCategory = builder.moveCategory;
		this.nebulaMode = builder.nebulaMode;
		this.constellationMode = builder.constellationMode;
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

	public PokemonType getMoveType() {
		return moveType;
	}

	public MoveCategory getMoveCategory() {
		return moveCategory;
	}

	public NebulaMode getNebulaMode() {
		return nebulaMode;
	}

	public ConstellationMode getConstellationMode() {
		return constellationMode;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder extends Move.Builder<Builder> {
		private final String starPartnerId;
		private final PokemonMove movePrototype;
		private final PokemonType moveType;
		private final MoveCategory moveCategory;
		private NebulaMode nebulaMode;
		private ConstellationMode constellationMode;

		public Builder(String starPartnerId, int moveId, String moveCode, String movePrototype) {
			PokemonMove prototype = PokemonMoveRegistry.getPokemonMoveByCode(movePrototype);
			String prototypeId = String.format("%08d", prototype.getMoveIdNo());
			super("SNM_" + starPartnerId + prototypeId + String.format("%03d", moveId), moveCode);
			this.starPartnerId = starPartnerId;
			this.movePrototype = prototype;
			this.moveType = prototype.getMoveType();
			this.moveCategory = prototype.getMoveCategory();
		}

		public Builder setMode(NebulaMode nebulaMode, ConstellationMode constellationMode) {
			this.nebulaMode = nebulaMode;
			this.constellationMode = constellationMode;
			return this;
		}

		public StarNexusMove build() {
			return new StarNexusMove(this);
		}
	}
}
