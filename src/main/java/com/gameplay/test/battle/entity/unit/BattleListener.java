package com.gameplay.test.battle.entity.unit;

import com.gameplay.test.battle.entity.turn.BattleActionQueue;
import com.gameplay.test.battle.entity.turn.BattleTurn;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;

import java.util.List;

public interface BattleListener {

	//	Bắt đầu Turn chưa qua START Step
	default void onTurnBegin(BattleTurn battleTurn) {}

	default void onStartStep(BattleTurn battleTurn, BattlePokemon.StartStepResult startStepResult) {}

	//	Perform Tactic Move, vẫn ở STANDBY Step
	default void onTacticMoveUsed(BattleTurn battleTurn, TacticMove tacticMove) {}

	default void onPerformStep(BattleTurn battleTurn, double actionInterval) {}

	default void onMoveFailed(BattleTurn battleTurn) {}

	default void onTurnEnd(BattleTurn battleTurn) {}

	default void onUnitFainted(BattleUnit battleUnit) {}

	default void onActionQueueChanged(List<BattleActionQueue.ActionForecastSlot> upcoming) {}

	default void onBattleEnd() {}
}
