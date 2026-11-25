package com.gameplay.battle.entity.unit.instance;

import com.gameplay.data.masterdata.data.master.registry.Registry;

public class UnitInstanceRegistry {

	private final Registry<String, UnitInstance> unitInstanceRegistry;

	public UnitInstanceRegistry() {
		unitInstanceRegistry = new Registry<>("UnitInstanceRegistry", "UnitInstance", UnitInstance::getInstanceId, null);
	}

	public void register(UnitInstance unitInstance) {
		if (unitInstance == null) {
			return;
		}

		unitInstanceRegistry.register(unitInstance);
	}

	public UnitInstance get(String instanceId) {
		return unitInstanceRegistry.getById(instanceId);
	}
}
