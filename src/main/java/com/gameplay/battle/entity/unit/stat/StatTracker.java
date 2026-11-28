package com.gameplay.battle.entity.unit.stat;

import com.gameplay.battle.entity.unit.profile.UnitProfile;
import com.gameplay.battle.entity.unit.stat.modifier.StatModifier;
import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.content.EnemyProfile;
import com.gameplay.data.masterdata.template.unit.BaseStat;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

import java.util.ArrayList;
import java.util.List;

public class StatTracker {

	private final UnitProfile unitProfile;
	private BaseStat unitBaseStat;
	private final List<Integer> bonusBaseStat;
	private final StatModifier statModifier;
	private final StatStageTracker statStageTracker;

	public StatTracker(UnitProfile unitProfile, BaseStat unitBaseStat, StatModifier statModifier) {
		this.unitProfile = unitProfile;
		this.unitBaseStat = unitBaseStat.copy();
		this.bonusBaseStat = new ArrayList<>();
		this.statModifier = statModifier.copy();
		this.statStageTracker = new StatStageTracker();
	}

	public StatTracker(UnitProfile unitProfile, BaseStat unitBaseStat) {
		this.unitProfile = unitProfile;
		this.unitBaseStat = unitBaseStat.copy();
		this.bonusBaseStat = new ArrayList<>();
		if (unitProfile instanceof OwnedStarPartner<?> ownedStarPartner) {
			this.statModifier = ownedStarPartner.getStatModifier().copy();
		} else {
			this.statModifier = new StatModifier();
		}
		this.statStageTracker = new StatStageTracker();
	}

	public UnitProfile getUnitProfile() {
		return unitProfile;
	}

	public StatModifier getStatModifier() {
		return statModifier;
	}

	public BaseStat getUnitBaseStat() {
		return unitBaseStat;
	}

	public void switchBaseStat(BaseStat newBaseStat) {
		this.unitBaseStat = newBaseStat.copy();
	}

	public List<Integer> getBonusBaseStat() {
		return bonusBaseStat;
	}

	public StatStageTracker getStatStageTracker() {
		return statStageTracker;
	}

	public StatTracker copy() {
		return new StatTracker(this.unitProfile, this.unitBaseStat, this.statModifier);
	}

	public int getStat(StatType statType) {
		int evolutionRank = (this.unitProfile instanceof OwnedStarPartner<?> ownedStarPartner) ? ownedStarPartner.getNexusEvolutionRank() : 0;
		double base = unitBaseStat.getBaseStat(statType, unitProfile.getLevel(), evolutionRank) + bonusBaseStat.get(statType.ordinal());

		double hpThreshold = (this.unitProfile instanceof EnemyProfile enemyInstance) ? enemyInstance.getHpThreshold() : 1;
		hpThreshold = (statType == StatType.HP) ? hpThreshold : 1.0;

		int statStage = statStageTracker.getStatStage(statType);

		double statStageMultiplier = (statStage >= 0) ? (1 + statStage * 0.1) : ((double) 1 / (1 - statStage * 0.1));

		double finalStat = base * (1 + statModifier.getStatMultiplier().getStatMultiplier(statType)) * hpThreshold * statStageMultiplier +
				statModifier.getFlatStat().getFlatStat(statType);

		return Math.max(1, (int) Math.round(base));
	}
}
