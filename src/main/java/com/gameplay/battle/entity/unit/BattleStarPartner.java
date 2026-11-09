package com.gameplay.battle.entity.unit;

import com.gameplay.masterdata.template.starpartner.PartnerState;
import com.gameplay.profile.entity.starpartner.OwnedStarPartner;
import com.utils.StringUtils;

public class BattleStarPartner {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final OwnedStarPartner starPartner;
	private PartnerState currentState;

	private final BattlePokemon partnerPokemon;
	private final BattleTrainer partnerTrainer;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public BattleStarPartner(OwnedStarPartner starPartner) {
		this.starPartner = starPartner;
		this.currentState = starPartner.getStarPartner().getDefaultPartnerState();

		this.partnerPokemon = new BattlePokemon(starPartner.getStarPartner().getPartnerPokemon(), this.currentState);
		this.partnerTrainer = new BattleTrainer(starPartner);
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public OwnedStarPartner getStarPartner() {
		return starPartner;
	}

	public BattlePokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	public BattleTrainer getPartnerTrainer() {
		return partnerTrainer;
	}

	//==================================================================================================================
	//	Partner State Management
	//==================================================================================================================

	public PartnerState getCurrentState() {
		return currentState;
	}

	public record StateChangeCheck(boolean stateChanged, boolean pokemonFormChanged) {
		static final StateChangeCheck NONE = new StateChangeCheck(false, false);
	}

	public StateChangeCheck changeState(PartnerState partnerState) {
		if (partnerState == null || currentState == partnerState) {
			return StateChangeCheck.NONE;
		}

		currentState = partnerState;

		boolean formChanged = partnerState.getPartnerPokemonForm() != null && partnerPokemon.changeForm(partnerState.getPartnerPokemonForm());

		return new StateChangeCheck(true, formChanged);
	}

	public StateChangeCheck changeState(int stateId) {
		if (stateId < 0) {
			return StateChangeCheck.NONE;
		}

		PartnerState partnerState = starPartner.getStarPartner().getStarPartnerStateById(stateId);

		if (partnerState == null) {
			return StateChangeCheck.NONE;
		}

		return changeState(partnerState);
	}

	public StateChangeCheck changeState(String stateCode) {
		if (StringUtils.isNullOrBlank(stateCode)) {
			return StateChangeCheck.NONE;
		}

		try {
			return changeState(starPartner.getStarPartner().getStarPartnerStateByCode(stateCode));
		} catch (IllegalArgumentException e) {
			return StateChangeCheck.NONE;
		}
	}
}
