package com.gameplay.battle.entity.unit;

import com.gameplay.battle.entity.unit.pokemon.StatStageTracker;
import com.gameplay.masterdata.attribute.battle.InterferenceCondition;
import com.gameplay.masterdata.attribute.battle.StatusCondition;
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.masterdata.template.starpartner.PartnerPokemon;
import com.gameplay.masterdata.template.starpartner.PartnerState;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public class BattlePokemon {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private static final Random RANDOM = new Random();

	private final PartnerPokemon partnerPokemon;
	private PokemonForm currentForm;

	private final StatStageTracker statStageTracker;

	private StatusCondition currentStatusCondition;
	private int asleepTurn;

	private final Set<InterferenceCondition> interferenceConditionSet;
	private int confusedTurn;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected BattlePokemon(PartnerPokemon partnerPokemon, PartnerState currentState) {
		this.partnerPokemon = partnerPokemon;
		this.currentForm = currentState.getPartnerPokemonForm();

		this.statStageTracker = new StatStageTracker();

		this.currentStatusCondition = StatusCondition.NONE;
		this.asleepTurn = 0;

		this.interferenceConditionSet = EnumSet.noneOf(InterferenceCondition.class);
		this.confusedTurn = 0;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public PartnerPokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	public StatStageTracker getStatStageTracker() {
		return statStageTracker;
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

	public int getAsleepTurn() {
		if (currentStatusCondition == StatusCondition.SLEEP) {
			return asleepTurn;
		}
		return 0;
	}

	public boolean getInflictedWith(StatusCondition statusCondition) {
		if (statusCondition == null || statusCondition == StatusCondition.NONE || this.currentStatusCondition != StatusCondition.NONE) {
			return false;
		}

		this.currentStatusCondition = statusCondition;
		if (this.currentStatusCondition == StatusCondition.SLEEP) {
			this.asleepTurn = RANDOM.nextInt(1, 4);
		}

		return true;
	}

	public boolean removeStatusCondition() {
		if (this.currentStatusCondition == StatusCondition.NONE) {
			return false;
		}

		this.currentStatusCondition = StatusCondition.NONE;
		this.asleepTurn = 0;

		return true;
	}

	//==================================================================================================================
	//	Interference Condition Management
	//==================================================================================================================

	public boolean isInflictedByAInterferenceCondition() {
		return !interferenceConditionSet.isEmpty();
	}

	public boolean isConfused() {
		return interferenceConditionSet.contains(InterferenceCondition.CONFUSION);
	}

	public boolean isInfatuated() {
		return interferenceConditionSet.contains(InterferenceCondition.INFATUATION);
	}

	public boolean isFlinching() {
		return interferenceConditionSet.contains(InterferenceCondition.FLINCH);
	}

	public boolean getInflictedWith(InterferenceCondition interferenceCondition) {
		if (interferenceCondition == null || !this.interferenceConditionSet.add(interferenceCondition)) {
			return false;
		}

		switch (interferenceCondition) {
			case CONFUSION -> this.confusedTurn = RANDOM.nextInt(2, 6);
			default -> {}
		}

		return true;
	}

	public boolean removeInterferenceCondition(InterferenceCondition interferenceCondition) {
		if (!interferenceConditionSet.remove(interferenceCondition)) {
			return false;
		}

		switch (interferenceCondition) {
			case CONFUSION -> this.confusedTurn = 0;
			default -> {}
		}

		return true;
	}

	public boolean removeInterferenceCondition() {
		if (interferenceConditionSet.isEmpty()) {
			return false;
		}

		this.interferenceConditionSet.clear();
		this.confusedTurn = 0;

		return true;
	}
}
