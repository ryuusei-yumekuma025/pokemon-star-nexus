package com.gameplay.battle.entity.unit.stat;

import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.data.config.StatConfig;

import java.util.EnumMap;

public class StatStageTracker {

	private final EnumMap<StatType, Integer> statStages = new EnumMap<>(StatType.class);

	public StatStageTracker() {
		resetStatStage();
	}

	public int getStatStage(StatType statType) {
		return statStages.get(statType);
	}

	public int modifyStat(StatType statType, int deltaStage) {
		if (!statType.canModify()) {
			return 0;
		}

		int currentStatStage = statStages.get(statType);
		int statStageAfterModify = Math.clamp(currentStatStage + deltaStage, StatConfig.MIN_STAT_STAGE, StatConfig.MAX_STAT_STAGE);
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
