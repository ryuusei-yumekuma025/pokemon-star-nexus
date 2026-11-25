package com.gameplay.battle.entity.unit.stat;

public class StatSystem {

	private final StatTracker statTracker;
	private final StatStageTracker statStageTracker;

	public StatSystem(StatTracker statTracker) {
		this.statTracker = statTracker.copy();
		this.statStageTracker = new StatStageTracker();
	}

	public StatTracker getStatTracker() {
		return statTracker;
	}

	public StatStageTracker getStatStageTracker() {
		return statStageTracker;
	}
}
