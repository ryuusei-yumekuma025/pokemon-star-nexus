package com.gameplay.battle.entity.unit;

import com.gameplay.masterdata.template.starpartner.PartnerState;
import com.gameplay.profile.unit.starpartner.OwnedStarPartner;
import com.utils.StringUtils;

public class BattleStarPartner {

	//==================================================================================================================
	//	Fields
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
		this.partnerTrainer = new BattleTrainer(starPartner.getStarPartner().getPartnerTrainer());
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public OwnedStarPartner getStarPartner() {
		return starPartner;
	}

	public PartnerState getCurrentState() {
		return currentState;
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

	public boolean changeState(int stateId) {
		if (stateId < 0) {
			return false;
		}

		PartnerState partnerState = starPartner.getStarPartner().getStarPartnerStateById(stateId);

		if (partnerState == null || this.currentState == partnerState) {
			return false;
		}

		this.currentState = partnerState;
		this.partnerPokemon.changeForm(partnerState.getPartnerPokemonForm());

		return true;
	}

	public boolean changeState(String stateCode) {
		if (StringUtils.isNullOrBlank(stateCode)) {
			return false;
		}

		stateCode = StringUtils.upperSnakeCase(stateCode);

		PartnerState partnerState = starPartner.getStarPartner().getStarPartnerStateByCode(stateCode);

		if (partnerState == null || this.currentState == partnerState) {
			return false;
		}

		this.currentState = partnerState;
		this.partnerPokemon.changeForm(partnerState.getPartnerPokemonForm());

		return true;
	}
}
