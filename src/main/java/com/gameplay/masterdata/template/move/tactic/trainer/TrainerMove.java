package com.gameplay.masterdata.template.move.tactic.trainer;

import com.gameplay.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.masterdata.template.move.tactic.TacticMove;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;

public class TrainerMove extends TacticMove {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final String trainerAlternateId;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private TrainerMove(Builder builder) {
		super(builder);
		this.trainerAlternateId = builder.trainerAlternateId;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	@Override
	public int getMoveIdNo() {
		return Integer.parseInt(getMoveId().substring(getMoveId().length() - 3));
	}

	public TrainerAlternate getTrainerAlternate() {
		return TrainerRegistry.getTrainerAlternateById(trainerAlternateId);
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder extends TacticMove.Builder<Builder> {
		private final String trainerAlternateId;

		public Builder(String trainerAlternateId, int moveId, String moveCode) {
			super("TM_" + trainerAlternateId + String.format("%03d", moveId), moveCode);
			this.trainerAlternateId = trainerAlternateId;
		}

		public TrainerMove build() {
			return new TrainerMove(this);
		}
	}
}
