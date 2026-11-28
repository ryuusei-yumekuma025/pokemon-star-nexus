package com.gameplay.test.battle.entity.fieldeffect.side;

import com.gameplay.test.battle.entity.fieldeffect.side.terraatlas.TerraAtlasOnBattleField;

public class SideBattleFieldEffect {

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	private final boolean isAlliedSide;
	private final TerraAtlasOnBattleField terraAtlas;

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public SideBattleFieldEffect(boolean isAlliedSide) {
		this.isAlliedSide = isAlliedSide;
		this.terraAtlas = new TerraAtlasOnBattleField();
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public boolean isAlliedSide() {
		return isAlliedSide;
	}

	public TerraAtlasOnBattleField getTerraAtlas() {
		return terraAtlas;
	}
}
