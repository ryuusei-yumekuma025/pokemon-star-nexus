package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.data.master.registry.move.starnexus.StarNexusMoveRegistry;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.template.move.starnexus.StarNexusMove;
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.utils.StringUtils;

public class PartnerState {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final int stateId;
	private final String stateCode;
	private final PokemonForm partnerPokemonForm;
	private final StarNexusMove starNexusMove;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PartnerState(Builder builder) {
		this.stateId = builder.stateId;
		this.stateCode = builder.stateCode;
		this.partnerPokemonForm = builder.partnerPokemonForm;
		this.starNexusMove = builder.starNexusMove;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getStateId() {
		return stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public PokemonForm getPartnerPokemonForm() {
		return partnerPokemonForm;
	}

	public StarNexusMove getStarNexusMove() {
		return starNexusMove;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final int stateId;
		private final String stateCode;
		private PokemonForm partnerPokemonForm;
		private StarNexusMove starNexusMove;

		public Builder(int stateId, String stateCode) {
			this.stateId = stateId;
			this.stateCode = StringUtils.upperSnakeCase(stateCode);
		}

		public Builder setPartnerPokemonForm(String pokemonForm) {
			this.partnerPokemonForm = PokemonRegistry.getPokemonFormByCode(pokemonForm);
			return this;
		}

		public Builder setStarNexusMove(String starNexusMove) {
			this.starNexusMove = StarNexusMoveRegistry.getStarNexusMoveByCode(starNexusMove);
			return this;
		}

		public PartnerState build() {
			return new PartnerState(this);
		}
	}
}
