package com.gameplay.battle.entity.turn;

import com.gameplay.battle.entity.unit.BattleListener;
import com.gameplay.battle.entity.unit.BattlePokemon;
import com.gameplay.battle.entity.unit.BattleUnit;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;

public class BattleTurn {

	private final BattleUnit battleUnit;
	private final BattleActionQueue battleActionQueue;

	private TurnStep turnStep;

	//	Chặn hàm end() khi chưa start()
	private boolean started;
	//	Chặn các hàm khác khi kết thúc tránh xen vào khi lượt đã xong
	private boolean finished;
	private boolean canPerform;
	private BattlePokemon.StartStepResult startStepResult;
	private boolean performed;
	private boolean moveFailed;

	private double actionInterval;

	private BattleListener battleListener;

	BattleTurn(BattleUnit battleUnit, BattleActionQueue battleActionQueue) {
		this.battleUnit = battleUnit;
		this.battleActionQueue = battleActionQueue;
		this.turnStep = TurnStep.START;
	}

	void setBattleListener(BattleListener battleListener) {
		this.battleListener = battleListener;
	}

	public BattleUnit getBattleUnit() {
		return battleUnit;
	}

	public TurnStep getTurnStep() {
		return turnStep;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean canPerform() {
		return canPerform;
	}

	public BattlePokemon.StartStepResult getStartStepResult() {
		return startStepResult;
	}

	public boolean hasPerformed() {
		return performed;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Steps																											*/

	public boolean start() {
		if (finished || started) {
			return canPerform;
		}

		started = true;

		if (battleUnit.getBattleTrainer() != null) {
			battleUnit.getBattleTrainer().startStep();
		}

		startStepResult = battleUnit.getBattlePokemon().startStep();
		canPerform = startStepResult.canAct();

		turnStep = TurnStep.STANDBY;

		if (battleListener != null) {
			battleListener.onStartStep(this, startStepResult);
		}

		return canPerform;
	}

	public boolean useTacticMove(TacticMove tacticMove) {
		if (finished || turnStep != TurnStep.STANDBY) {
			return false;
		}

		boolean used = battleUnit.useTacticMove(tacticMove);

		if (used && battleListener != null) {
			battleListener.onTacticMoveUsed(this, tacticMove);
		}

		return used;
	}

	public boolean performStep(double actionInterval, Runnable action) {
		if (finished || turnStep != TurnStep.STANDBY || !canPerform || performed || action == null) {
			return false;
		}

		this.turnStep = TurnStep.PERFORM;
		this.actionInterval = actionInterval;

		if (battleUnit.getBattlePokemon().isFullyParalyzed()) {
			this.moveFailed = true;
			this.performed = true;

			if (battleListener != null) {
				battleListener.onMoveFailed(this);
			}

			return true;
		}

		action.run();

		this.performed = true;

		if (this.battleListener != null) {
			battleListener.onPerformStep(this, this.actionInterval);
		}

		return true;
	}

	public boolean canEnd() {
		if (finished || !started) {
			return false;
		}

		return turnStep == TurnStep.PERFORM || (turnStep == TurnStep.STANDBY && !canPerform);
	}

	public boolean endStep() {
		if (!canEnd()) {
			return false;
		}

		this.turnStep = TurnStep.END;

		this.finished = true;

		if (this.battleListener != null) {
			battleListener.onTurnEnd(this);
		}

		return true;
	}
}
