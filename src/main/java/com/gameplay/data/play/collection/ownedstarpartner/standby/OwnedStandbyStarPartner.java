package com.gameplay.data.play.collection.ownedstarpartner.standby;

import com.gameplay.data.masterdata.template.unit.type.playable.standby.StandbyStarPartner;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

public class OwnedStandbyStarPartner extends OwnedStarPartner<StandbyStarPartner> {

	public OwnedStandbyStarPartner(StandbyStarPartner standbyStarPartner) {
		super(standbyStarPartner, "OWN" + standbyStarPartner.getStarUnitId());
	}
}
