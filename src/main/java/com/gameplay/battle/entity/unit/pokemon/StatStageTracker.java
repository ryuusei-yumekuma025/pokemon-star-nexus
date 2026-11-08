package com.gameplay.battle.entity.unit.pokemon;

import com.gameplay.masterdata.attribute.stat.StatType;

import java.util.EnumMap;
import java.util.Map;

public class StatStageTracker {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	public static final int MAX_STAT_STAGE = 6;
	public static final int MIN_STAT_STAGE = -6;

	private final EnumMap<StatType, Integer> statStages = new EnumMap<>(StatType.class);

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public StatStageTracker() {
		resetStatStage();
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getStatStage(StatType statType) {
		return statStages.get(statType);
	}

	//==================================================================================================================
	//	Modifier
	//==================================================================================================================

	public int modifyStat(StatType statType, int deltaStage) {
		if (!statType.canModify()) {
			return 0;
		}

		int currentStatStage = statStages.get(statType);
		int statStageAfterModify = Math.clamp(currentStatStage + deltaStage, MIN_STAT_STAGE, MAX_STAT_STAGE);
		statStages.put(statType, statStageAfterModify);

		return statStageAfterModify - currentStatStage;
	}

	public void resetStatStage() {
		for (StatType statType : StatType.values()) {
			statStages.put(statType, 0);
		}
	}

	public void resetRaisedStatStage() {
		statStages.replaceAll((statType, statStage) -> Math.min(statStage, 0));
	}

	public void reverseStatStage() {
		statStages.replaceAll((statType, statStage) -> -statStage);
	}

	public void reverseRaisedStatStage() {
		statStages.replaceAll((statType, statStage) -> (statStage > 0) ? -statStage : statStage);
	}

	public void copyStatStage(StatStageTracker targetedStatStageTracker) {
		this.statStages.putAll(targetedStatStageTracker.statStages);
	}
}
