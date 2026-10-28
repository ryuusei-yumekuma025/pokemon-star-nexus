package com.gameplay.masterdata.template.move.tactic.item;

import com.gameplay.masterdata.template.move.tactic.TacticMove;

public class ItemMove extends TacticMove {

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private ItemMove(Builder builder) {
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

	public static class Builder extends TacticMove.Builder<Builder> {
		public Builder(int moveId, String moveCode) {
			super("IM_" + String.format("%08d", moveId), moveCode);
		}

		@Override
		public ItemMove build() {
			return new ItemMove(this);
		}
	}
}
