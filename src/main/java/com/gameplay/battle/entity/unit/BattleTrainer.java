package com.gameplay.battle.entity.unit;

import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;

import java.util.Map;

public class BattleTrainer {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final TrainerAlternate partnerTrainer;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected BattleTrainer(StarPartner starPartner) {
		this.partnerTrainer = starPartner.getPartnerTrainer();
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public TrainerAlternate getPartnerTrainer() {
		return partnerTrainer;
	}
}
