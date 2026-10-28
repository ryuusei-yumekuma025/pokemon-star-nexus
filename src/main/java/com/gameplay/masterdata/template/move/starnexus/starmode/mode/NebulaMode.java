package com.gameplay.masterdata.template.move.starnexus.starmode.mode;

import com.gameplay.masterdata.template.move.starnexus.starmode.StarMoveMode;

public class NebulaMode extends StarMoveMode {

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private NebulaMode(Builder builder) {
		super(builder);
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder extends StarMoveMode.Builder<Builder> {
		@Override
		public NebulaMode build() {
			return new NebulaMode(this);
		}
	}
}
