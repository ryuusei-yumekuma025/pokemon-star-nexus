package com.gameplay.masterdata.template.move;

import com.utils.StringUtils;

public abstract class Move {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final String moveId;
	private final String moveCode;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected Move(Builder<?> builder) {
		this.moveId = builder.moveId;
		this.moveCode = builder.moveCode;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public String getMoveId() {
		return moveId;
	}

	public abstract int getMoveIdNo();

	public String getMoveCode() {
		return moveCode;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public abstract static class Builder<B extends Builder<B>> {
		private final String moveId;
		private final String moveCode;

		protected Builder(String moveId, String moveCode) {
			this.moveId = StringUtils.upperSnakeCase(moveId);
			this.moveCode = StringUtils.upperSnakeCase(moveCode);
		}

		@SuppressWarnings("unchecked")
		protected B self() {
			return (B) this;
		}

		public abstract Move build();
	}
}
