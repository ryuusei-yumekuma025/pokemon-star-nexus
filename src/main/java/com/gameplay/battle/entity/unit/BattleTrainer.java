package com.gameplay.battle.entity.unit;

import com.gameplay.masterdata.template.trainer.TrainerAlternate;

public class BattleTrainer {

	//==================================================================================================================
	//	Fields
	//==================================================================================================================

	private final TrainerAlternate partnerTrainer;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected BattleTrainer(TrainerAlternate partnerTrainer) {
		this.partnerTrainer = partnerTrainer;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public TrainerAlternate getPartnerTrainer() {
		return partnerTrainer;
	}
}
