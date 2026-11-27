package com.gameplay.data.play.collection.ownedstarpartner;

import com.gameplay.battle.entity.unit.instance.UnitInstance;
import com.gameplay.battle.entity.unit.stat.modifier.StatModifier;
import com.gameplay.data.masterdata.data.config.LevelConfig;
import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.unit.type.playable.PlayableStarPartner;
import com.gameplay.data.play.collection.ownedstarpartner.standby.OwnedStandbyStarPartner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class OwnedStarPartner<SP extends PlayableStarPartner> implements UnitInstance {

	private final String instanceId;
	private final SP starPartner;
	private final Instant uniteTime;

	private int level;
	private int nexusEvolutionRank;
	private long currentEXP;

	private final List<Move> moveset;
	private final StatModifier statModifier;

	protected OwnedStarPartner(SP starPartner, String instanceId) {
		this.instanceId = instanceId;
		this.starPartner = starPartner;
		this.uniteTime = Instant.now();
		this.level = 1;
		this.currentEXP = 0L;
		this.moveset = new ArrayList<>();
		this.statModifier = new StatModifier();
	}

	@Override
	public String getInstanceId() {
		return instanceId;
	}

	@Override
	public SP getStarUnit() {
		return starPartner;
	}

	public Instant getUniteTime() {
		return uniteTime;
	}

	public String getUniteDate() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());
		return dateTimeFormatter.format(uniteTime);
	}

	public String getUniteDateAndTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy").withZone(ZoneId.systemDefault());
		return dateTimeFormatter.format(uniteTime);
	}

	@Override
	public int getLevel() {
		return level;
	}

	public int getLevelCap() {
		return LevelConfig.getLevelCap(this.nexusEvolutionRank);
	}

	public int getNexusEvolutionRank() {
		return nexusEvolutionRank;
	}

	public void unlockLevelCap() {
		if (nexusEvolutionRank >= LevelConfig.MAX_EVOLUTION_RANK) {
			return;
		}

		nexusEvolutionRank++;
	}

	public long getCurrentEXP() {
		return currentEXP;
	}

	public long getEXPToNextLevel() {
		if (level >= getLevelCap()) {
			return 0L;
		}
		return Math.round(1.2 * Math.pow(level, 3) - 15 * Math.pow(level, 2) + 100 * level - 67);
	}

	public void gainEXP(long exp) {
		if (exp <= 0 || this.level >= getLevelCap()) {
			return;
		}

		currentEXP += exp;

		while (level < getLevelCap() && currentEXP >= getEXPToNextLevel()) {
			currentEXP -= getEXPToNextLevel();
			level++;
		}

		if (level >= getLevelCap()) {
			currentEXP = 0;
		}
	}

	public List<Move> getMoveset() {
		return moveset;
	}

	public boolean addMove(Move move) {
		int maxSize = (this instanceof OwnedStandbyStarPartner) ? 2 : 6;
		if (moveset.size() >= maxSize) {
			return false;
		}

		this.moveset.add(move);
		return true;
	}

	public boolean removeMove(int slot) {
		if (slot <= 0 || slot > moveset.size()) {
			return false;
		}

		this.moveset.remove(slot);
		return true;
	}

	public StatModifier getStatModifier() {
		return statModifier;
	}
}
