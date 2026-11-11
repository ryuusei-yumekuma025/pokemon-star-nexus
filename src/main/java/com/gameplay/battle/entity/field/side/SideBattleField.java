package com.gameplay.battle.entity.field.side;

import com.gameplay.battle.entity.field.side.terraatlas.TerraAtlasOnBattleField;

public class SideBattleField {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final boolean isAlliedSide;
	private final TerraAtlasOnBattleField terraAtlas;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public SideBattleField(boolean isAlliedSide) {
		this.isAlliedSide = isAlliedSide;
		this.terraAtlas = new TerraAtlasOnBattleField();
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public boolean isAlliedSide() {
		return isAlliedSide;
	}

	public TerraAtlasOnBattleField getTerraAtlas() {
		return terraAtlas;
	}
}
