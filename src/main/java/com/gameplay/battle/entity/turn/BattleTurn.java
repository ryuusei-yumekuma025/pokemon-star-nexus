package com.gameplay.battle.entity.turn;

import com.gameplay.battle.entity.unit.BattleUnit;

public class BattleTurn {

	/*------------------------------------------------------------------------------------------------------------------
	.	Fields																											*/

	private final BattleUnit battleUnit;

	private TurnStep turnStep;

	/*------------------------------------------------------------------------------------------------------------------
	.	Constructor																										*/

	public BattleTurn(BattleUnit battleUnit) {
		this.battleUnit = battleUnit;

		this.turnStep = TurnStep.START;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Getters																											*/

	public BattleUnit getBattleUnit() {
		return battleUnit;
	}
}
