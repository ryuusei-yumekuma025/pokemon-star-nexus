package com.gameplay.test.battle.entity.unit;

import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;
import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleTrainer {

	public static final int UNLIMITED = -1;

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final BattleUnit battleUnit;
	private final Trainer partnerTrainer;
	private final Map<String, Integer> tacticPointTracker;
	private final Map<String, Integer> maxTacticPoint;

	private int tacticPointUsedThisTurn;
	private int tacticPointLimitPerTurn = UNLIMITED;

	/*------------------------------------------------------------------------------------------------------------------
																														*/

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

		this.maxTacticPoint = Collections.unmodifiableMap(tacticPointTracker);
		this.tacticPointTracker = new HashMap<>(this.maxTacticPoint);
	}

	/*------------------------------------------------------------------------------------------------------------------
	.																													*/

	public BattleUnit getBattleUnit() {
		return battleUnit;
	}

	public Trainer getPartnerTrainer() {
		return partnerTrainer;
	}

	public Map<String, Integer> getTacticPointTracker() {
		return Collections.unmodifiableMap(tacticPointTracker);
	}

	public Map<String, Integer> getMaxTacticPoint() {
		return maxTacticPoint;
	}

	public int getTacticPoint(TacticMove move) {
		if (move == null) {
			return -1;
		}
		return tacticPointTracker.getOrDefault(move.getMoveCode(), -1);
	}

	public int getTacticPoint(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode) || !tacticPointTracker.containsKey(StringUtils.upperSnakeCase(moveCode))) {
			return -1;
		}
		moveCode = StringUtils.upperSnakeCase(moveCode);
		return tacticPointTracker.get(moveCode);
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public boolean canPerform(TacticMove tacticMove) {
		if (tacticPointLimitPerTurn != UNLIMITED && this.tacticPointUsedThisTurn >= this.tacticPointLimitPerTurn) {
			return false;
		}

		return getTacticPoint(tacticMove) > 0;
	}

	public boolean performMove(TacticMove tacticMove) {
		if (!canPerform(tacticMove)) {
			return false;
		}

		String code = tacticMove.getMoveCode();

		Integer points = tacticPointTracker.get(code);

		if (points == null || points <= 0 || !tacticPointTracker.containsKey(code)) {
    		return false;
		}

		tacticPointTracker.put(code, points - 1);

		tacticPointUsedThisTurn++;

		return true;
	}

	public int restoreTacticPoint(TacticMove tacticMove, int restoredAmount) {
		if (restoredAmount <= 0 || tacticMove == null) {
			return 0;
		}

		String code = tacticMove.getMoveCode();
		Integer current = tacticPointTracker.get(code);
		if (current == null) {
			return 0;
		}

		int restored = Math.min(maxTacticPoint.get(code) - current, restoredAmount);
		tacticPointTracker.put(code, current + restored);

		return restored;
	}

	public void fullyRestoreAllTacticMove() {
		tacticPointTracker.putAll(this.maxTacticPoint);
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Turn																											*/

	public void startStep() {
		this.tacticPointUsedThisTurn = 0;
	}

	public int getTacticPointUsedThisTurn() {
		return tacticPointUsedThisTurn;
	}

	public int getTacticPointLimitPerTurn() {
		return tacticPointLimitPerTurn;
	}

	public void setTacticPointLimitPerTurn(int limit) {
		this.tacticPointLimitPerTurn = (limit < 0) ? UNLIMITED : limit;
	}
}
