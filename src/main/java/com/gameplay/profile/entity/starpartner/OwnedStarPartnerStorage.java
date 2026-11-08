package com.gameplay.profile.entity.starpartner;

import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.utils.StringUtils;

import java.util.*;

public class OwnedStarPartnerStorage {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final Map<String, OwnedStarPartner> ownedStarPartnerStorage;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public OwnedStarPartnerStorage() {
		this.ownedStarPartnerStorage = new LinkedHashMap<>();
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public Collection<OwnedStarPartner> getStarPartnerList() {
		return Collections.unmodifiableCollection(ownedStarPartnerStorage.values());
	}

	public OwnedStarPartner getStarPartner(String id) {
		if (StringUtils.isNullOrBlank(id)) {
			throw new IllegalArgumentException("Missing Star Partner Id in Storage.");
		}

		return ownedStarPartnerStorage.get(StringUtils.upperSnakeCase(id));
	}

	//==================================================================================================================
	//	Star Partner Unite
	//==================================================================================================================

	public void starPartnerUnite(StarPartner starPartner) {
		if (ownedStarPartnerStorage.containsKey(starPartner.getStarPartnerId())) {
			return;
		}

		this.ownedStarPartnerStorage.put(starPartner.getStarPartnerId(), new OwnedStarPartner(starPartner));
	}
}
