package com.gameplay.data.masterdata.template.unit.type.playable.standby;

import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.trainer.instance.TrainerAlternate;
import com.gameplay.data.masterdata.template.unit.type.playable.PlayableStarPartner;

public class StandbyStarPartner extends PlayableStarPartner {

	private StandbyStarPartner(Builder builder) {
		super(builder);
	}

	public static class Builder extends PlayableStarPartner.Builder<Builder> {
		public Builder(int starPartnerId, String starPartnerCode, String trainerAlternateCode) {
			TrainerAlternate trainerAlternate = TrainerRegistry.getAlternateByCode(trainerAlternateCode);
			super("SSP_" + trainerAlternate.getTrainerAlternateId() + String.format("%04d", starPartnerId),
					starPartnerCode, trainerAlternate);
		}

		@Override
		public StandbyStarPartner build() {
			return new StandbyStarPartner(this);
		}
	}
}
