package com.gameplay.battle.entity.field.side;

import com.gameplay.battle.entity.field.side.terraatlas.TerraAtlasOnBattleField;

public class SideBattleField {

	private final boolean isAlliedSide;
	private final TerraAtlasOnBattleField terraAtlas;

	public SideBattleField(boolean isAlliedSide) {
		this.isAlliedSide = isAlliedSide;
		this.terraAtlas = new TerraAtlasOnBattleField();
	}

	public boolean isAlliedSide() {
		return isAlliedSide;
	}

	public TerraAtlasOnBattleField getTerraAtlas() {
		return terraAtlas;
	}
}
