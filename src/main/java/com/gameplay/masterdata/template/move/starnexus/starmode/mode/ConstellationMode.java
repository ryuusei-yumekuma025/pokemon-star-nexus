package com.gameplay.masterdata.template.move.starnexus.starmode.mode;

import com.gameplay.masterdata.template.move.starnexus.starmode.StarMoveMode;

public class ConstellationMode extends StarMoveMode {

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private ConstellationMode(Builder builder) {
		super(builder);
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder extends StarMoveMode.Builder<Builder> {
		@Override
		public ConstellationMode build() {
			return new ConstellationMode(this);
		}
	}
}
