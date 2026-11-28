package com.gameplay.battle.entity.unit.profile;

import com.gameplay.data.masterdata.data.master.registry.Registry;

public class UnitProfileRegistry {

	private final Registry<String, UnitProfile> unitProfileRegistry;

	public UnitProfileRegistry() {
		unitProfileRegistry = new Registry<>("UnitProfileRegistry", "UnitProfile", UnitProfile::getProfileId, null);
	}

	public void register(UnitProfile unitProfile) {
		if (unitProfile == null) {
			return;
		}

		unitProfileRegistry.register(unitProfile);
	}

	public UnitProfile get(String profileId) {
		return unitProfileRegistry.getById(profileId);
	}
}
