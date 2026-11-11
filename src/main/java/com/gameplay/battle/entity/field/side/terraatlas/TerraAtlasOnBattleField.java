package com.gameplay.battle.entity.field.side.terraatlas;

import com.gameplay.masterdata.attribute.battle.field.side.TerraAtlas;

import java.util.*;

public class TerraAtlasOnBattleField {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final Map<TerraAtlas, Integer> terraAtlasDurationMap;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public TerraAtlasOnBattleField() {
		this.terraAtlasDurationMap = new LinkedHashMap<>();
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public Set<TerraAtlas> getActivatedTerraAtlasSet() {
		return new LinkedHashSet<>(terraAtlasDurationMap.keySet());
	}

	public int getDuration(TerraAtlas terraAtlas) {
		return terraAtlasDurationMap.getOrDefault(terraAtlas, 0);
	}

	public boolean isActivated(TerraAtlas terraAtlas) {
		return terraAtlasDurationMap.containsKey(terraAtlas);
	}

	//==================================================================================================================
	//	Management
	//==================================================================================================================

	public boolean applyTerraAtlasFieldEffect(TerraAtlas terraAtlas, int duration) {
		if (terraAtlas == null || duration <= 0) {
			return false;
		}

		this.terraAtlasDurationMap.put(terraAtlas, duration);
		return true;
	}

	public boolean modifyDuration(TerraAtlas terraAtlas, int deltaDuration) {
		if (terraAtlas == null) {
			return false;
		}

		Integer currentDuration = terraAtlasDurationMap.get(terraAtlas);

		if (currentDuration == null) {
			return false;
		}

		int durationAfterModify = currentDuration + deltaDuration;

		if (durationAfterModify <= 0) {
			this.terraAtlasDurationMap.remove(terraAtlas);
			return true;
		}

		this.terraAtlasDurationMap.put(terraAtlas, durationAfterModify);
		return true;
	}
}
