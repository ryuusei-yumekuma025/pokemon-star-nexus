package com.gameplay.battle.entity.unit;

import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;
import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleTrainer {

	private final BattleUnit battleUnit;
	private final Trainer partnerTrainer;
	private final Map<String, Integer> tacticPointTracker;

	protected BattleTrainer(BattleUnit battleUnit, Trainer trainer, List<TacticMove> tacticMoveList, List<Move> moveset) {
		this.battleUnit = battleUnit;
		this.partnerTrainer = trainer;

		Map<String, Integer> tacticPointTracker = new HashMap<>();

		for (TacticMove move : tacticMoveList) {
			tacticPointTracker.put(move.getMoveCode(), move.getTacticPoint());
		}

		for (Move move : moveset) {
			if (move instanceof TacticMove tacticMove) {
				tacticPointTracker.put(tacticMove.getMoveCode(), tacticMove.getTacticPoint());
			}
		}

		this.tacticPointTracker = Collections.unmodifiableMap(tacticPointTracker);
	}

	public BattleUnit getBattleUnit() {
		return battleUnit;
	}

	public Trainer getPartnerTrainer() {
		return partnerTrainer;
	}

	public Map<String, Integer> getTacticPointTracker() {
		return tacticPointTracker;
	}

	public int getTacticPoint(TacticMove move) {
		return tacticPointTracker.get(move.getMoveCode());
	}

	public int getTacticPoint(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode) || !tacticPointTracker.containsKey(StringUtils.upperSnakeCase(moveCode))) {
			return -1;
		}
		moveCode = StringUtils.upperSnakeCase(moveCode);
		return tacticPointTracker.get(moveCode);
	}

	public boolean performMove(TacticMove tacticMove) {
		if (tacticMove == null) {
			return false;
		}

		String code = tacticMove.getMoveCode();

		Integer points = tacticPointTracker.get(code);

		if (points == null || points <= 0 || !tacticPointTracker.containsKey(code)) {
    		return false;
		}

		tacticPointTracker.put(code, points - 1);

		return true;
	}
}
