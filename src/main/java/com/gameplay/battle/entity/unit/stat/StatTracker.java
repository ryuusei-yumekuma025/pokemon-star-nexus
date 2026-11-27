package com.gameplay.battle.entity.unit.stat;

import com.gameplay.battle.entity.unit.instance.UnitInstance;
import com.gameplay.battle.entity.unit.stat.modifier.FlatStat;
import com.gameplay.battle.entity.unit.stat.modifier.StatModifier;
import com.gameplay.battle.entity.unit.stat.modifier.StatMultiplier;
import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.content.EnemyInstance;
import com.gameplay.data.masterdata.template.unit.BaseStat;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

import java.util.ArrayList;
import java.util.List;

public class StatTracker {

	private final UnitInstance unitInstance;
	private BaseStat unitBaseStat;
	private final List<Integer> bonusBaseStat;
	private final StatModifier statModifier;
	private final StatStageTracker statStageTracker;

	public StatTracker(UnitInstance unitInstance, BaseStat unitBaseStat, StatModifier statModifier) {
		this.unitInstance = unitInstance;
		this.unitBaseStat = unitBaseStat.copy();
		this.bonusBaseStat = new ArrayList<>();
		this.statModifier = statModifier.copy();
		this.statStageTracker = new StatStageTracker();
	}

	public StatTracker(UnitInstance unitInstance, BaseStat unitBaseStat) {
		this.unitInstance = unitInstance;
		this.unitBaseStat = unitBaseStat.copy();
		this.bonusBaseStat = new ArrayList<>();
		if (unitInstance instanceof OwnedStarPartner<?> ownedStarPartner) {
			this.statModifier = ownedStarPartner.getStatModifier().copy();
		} else {
			this.statModifier = new StatModifier();
		}
		this.statStageTracker = new StatStageTracker();
	}

	public UnitInstance getUnitInstance() {
		return unitInstance;
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
		return new StatTracker(this.unitInstance, this.unitBaseStat, this.statModifier);
	}

	public int getStat(StatType statType) {
		int evolutionRank = (this.unitInstance instanceof OwnedStarPartner<?> ownedStarPartner) ? ownedStarPartner.getNexusEvolutionRank() : 0;
		double base = unitBaseStat.getBaseStat(statType, unitInstance.getLevel(), evolutionRank) + bonusBaseStat.get(statType.ordinal());

		double hpThreshold = (this.unitInstance instanceof EnemyInstance enemyInstance) ? enemyInstance.getHpThreshold() : 1;
		hpThreshold = (statType == StatType.HP) ? hpThreshold : 1.0;

		int statStage = statStageTracker.getStatStage(statType);

		double statStageMultiplier = (statStage >= 0) ? (1 + statStage * 0.1) : ((double) 1 / (1 - statStage * 0.1));

		double finalStat = base * (1 + statModifier.getStatMultiplier().getStatMultiplier(statType)) * hpThreshold * statStageMultiplier +
				statModifier.getFlatStat().getFlatStat(statType);

		return (int) Math.round(base);
	}
}
