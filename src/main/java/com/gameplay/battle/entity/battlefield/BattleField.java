package com.gameplay.battle.entity.battlefield;

import com.gameplay.battle.entity.fieldeffect.entire.EntireFieldEffect;

public class BattleField {

	/*------------------------------------------------------------------------------------------------------------------
	.	Battle Result																									*/

	public enum BattleResult {
		ONGOING,
		WIN,
		LOSE,
		DRAW;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Fields																											*/

	private final BattleSide alliedBattleSide = new BattleSide(this, true);
	private final BattleSide opponentBattleSide = new BattleSide(this, false);
	private final EntireFieldEffect entireFieldEffect = new EntireFieldEffect();

	private int turnCount = 0;
}
