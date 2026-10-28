package com.gameplay.masterdata.template.move.battle.pokemon;

import com.gameplay.masterdata.template.move.battle.BattleMove;

public class PokemonMove extends BattleMove {

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PokemonMove(Builder builder) {
		super(builder);
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	@Override
	public int getMoveIdNo() {
		return Integer.parseInt(getMoveId().substring(getMoveId().indexOf('_') + 1));
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder extends BattleMove.Builder<Builder> {
		public Builder(int moveId, String moveCode) {
			super("PM_" + String.format("%08d", moveId), moveCode);
		}

		@Override
		public PokemonMove build() {
			return new PokemonMove(this);
		}
	}
}
