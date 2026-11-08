package com.gameplay.battle.entity.unit;

import com.gameplay.masterdata.template.move.tactic.TacticMove;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.gameplay.profile.entity.starpartner.OwnedStarPartner;
import com.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BattleTrainer {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final TrainerAlternate partnerTrainer;
	private final Map<String, Integer> tacticPointTracker;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected BattleTrainer(OwnedStarPartner starPartner) {
		this.partnerTrainer = starPartner.getStarPartner().getPartnerTrainer();
		Map<String, Integer> tacticPointTracker = new HashMap<>();
		for (TacticMove move : starPartner.getStarPartner().getAllTacticMoves()) {
			tacticPointTracker.put(move.getMoveCode(), move.getTacticPoint());
		}
		this.tacticPointTracker = Collections.unmodifiableMap(tacticPointTracker);
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public TrainerAlternate getPartnerTrainer() {
		return partnerTrainer;
	}

	public Map<String, Integer> getTacticPointTracker() {
		return tacticPointTracker;
	}

	public int getTacticPoint(TacticMove move) {
		return tacticPointTracker.get(move.getMoveCode());
	}

	public int getTacticPoint(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			return -1;
		}
		moveCode = StringUtils.upperSnakeCase(moveCode);
		return tacticPointTracker.get(moveCode);
	}
}
