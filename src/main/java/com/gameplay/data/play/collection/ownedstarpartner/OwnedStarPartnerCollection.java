package com.gameplay.data.play.collection.ownedstarpartner;

import com.gameplay.battle.entity.unit.instance.UnitInstanceRegistry;
import com.gameplay.data.masterdata.template.unit.type.playable.PlayableStarPartner;
import com.gameplay.data.masterdata.template.unit.type.playable.personal.PersonalStarPartner;
import com.gameplay.data.masterdata.template.unit.type.playable.standby.StandbyStarPartner;
import com.gameplay.data.play.collection.ownedstarpartner.personal.OwnedPersonalStarPartner;
import com.gameplay.data.play.collection.ownedstarpartner.standby.OwnedStandbyStarPartner;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class OwnedStarPartnerCollection {

	private final Map<String, OwnedStandbyStarPartner> standbyStarPartnerStorage;
	private final Map<String, OwnedPersonalStarPartner> personalStarPartnerStorage;

	public OwnedStarPartnerCollection() {
		this.standbyStarPartnerStorage = new LinkedHashMap<>();
		this.personalStarPartnerStorage = new LinkedHashMap<>();
	}

	public OwnedStarPartner<?> getStarPartner(PlayableStarPartner playableStarPartner) {
		if (playableStarPartner instanceof PersonalStarPartner) {
			return personalStarPartnerStorage.get("OWN" + playableStarPartner.getStarUnitId());
		}
		return getStandbyStarPartner((StandbyStarPartner) playableStarPartner);
	}

	public OwnedStandbyStarPartner getStandbyStarPartner(StandbyStarPartner standbyStarPartner) {
		return standbyStarPartnerStorage.get("OWN" + standbyStarPartner.getStarUnitId());
	}

	public Collection<OwnedStandbyStarPartner> getAllOwnedStandbyStarPartner() {
		return standbyStarPartnerStorage.values();
	}

	public boolean uniteStandbyStarPartner(StandbyStarPartner standbyStarPartner) {
		if (standbyStarPartnerStorage.containsKey("OWN" + standbyStarPartner.getStarUnitId())) {
			return false;
		}

		OwnedStandbyStarPartner ownedStandbyStarPartner = new OwnedStandbyStarPartner(standbyStarPartner);
		this.standbyStarPartnerStorage.put("OWN" + standbyStarPartner.getStarUnitId(), ownedStandbyStarPartner);

		return true;
	}
}
