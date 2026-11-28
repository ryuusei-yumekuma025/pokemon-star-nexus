package com.gameplay.battle.entity.battlefield;

import com.gameplay.battle.entity.fieldeffect.side.SideFieldEffect;

public class BattleSide {

	/*------------------------------------------------------------------------------------------------------------------
	.	Fields																											*/

	private final BattleField battleField;
	private final boolean isAlliedSide;
	private final SideFieldEffect sideFieldEffect;

	/*------------------------------------------------------------------------------------------------------------------
	.	Constructor																										*/

	public BattleSide(BattleField battleField, boolean isAlliedSide) {
		this.battleField = battleField;
		this.isAlliedSide = isAlliedSide;
		this.sideFieldEffect = new SideFieldEffect();
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Getters																											*/

	public BattleField getBattleField() {
		return battleField;
	}

	public boolean isAlliedSide() {
		return isAlliedSide;
	}

	public SideFieldEffect getSideFieldEffect() {
		return sideFieldEffect;
	}
}
