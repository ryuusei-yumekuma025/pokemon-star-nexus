package com.gameplay.battle.entity.unit;

import com.gameplay.masterdata.attribute.battle.StatusCondition;
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.masterdata.template.starpartner.PartnerPokemon;
import com.gameplay.masterdata.template.starpartner.PartnerState;

public class BattlePokemon {

	//==================================================================================================================
	//	Fields
	//==================================================================================================================

	private final PartnerPokemon partnerPokemon;
	private PokemonForm currentForm;

	private StatusCondition currentStatusCondition;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected BattlePokemon(PartnerPokemon partnerPokemon, PartnerState currentState) {
		this.partnerPokemon = partnerPokemon;
		this.currentForm = currentState.getPartnerPokemonForm();

		this.currentStatusCondition = StatusCondition.NONE;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public PartnerPokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	//==================================================================================================================
	//	Pokemon Form Management
	//==================================================================================================================

	public PokemonForm getCurrentForm() {
		return currentForm;
	}

	public boolean changeForm(PokemonForm pokemonForm) {
		if (this.currentForm == pokemonForm) {
			return false;
		}

		this.currentForm = pokemonForm;

		return true;
	}

	//==================================================================================================================
	//	Status Condition Management
	//==================================================================================================================

	public StatusCondition getCurrentStatusCondition() {
		return currentStatusCondition;
	}

	public boolean isInflictedByAStatusCondition() {
		return currentStatusCondition != StatusCondition.NONE;
	}

	public boolean getInflictedWith(StatusCondition statusCondition) {
		if (statusCondition == null || statusCondition == StatusCondition.NONE || this.currentStatusCondition != StatusCondition.NONE) {
			return false;
		}

		this.currentStatusCondition = statusCondition;

		return true;
	}

	public boolean removeStatusCondition() {
		if (this.currentStatusCondition == StatusCondition.NONE) {
			return false;
		}

		this.currentStatusCondition = StatusCondition.NONE;

		return true;
	}
}
