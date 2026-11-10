package com.gameplay.profile.entity.starpartner;

import com.gameplay.masterdata.data.config.profile.entity.StarPartnerLevelConfig;
import com.gameplay.masterdata.template.starpartner.StarPartner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OwnedStarPartner {

	//==================================================================================================================
	//	Field, Properties and Info
	//==================================================================================================================

	private final StarPartner starPartner;
	private final Instant uniteTime;

	private int level;
	private int evolutionRank;
	private long currentEXP;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public OwnedStarPartner(StarPartner starPartner, Instant uniteTime) {
		this.starPartner = starPartner;
		this.uniteTime = uniteTime;

		this.level = 1;
		this.evolutionRank = 0;
		this.currentEXP = 0L;
	}

	public OwnedStarPartner(StarPartner starPartner) {
		this(starPartner, Instant.now());
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public StarPartner getStarPartner() {
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

	//==================================================================================================================
	//	Level and EXP Management
	//==================================================================================================================

	public int getLevel() {
		return level;
	}

	public int getLevelCap() {
		return StarPartnerLevelConfig.getLevelCap(this.evolutionRank);
	}

	public void unlockLevelCap() {
		if (evolutionRank >= StarPartnerLevelConfig.MAX_EVOLUTION_RANK) {
			return;
		}

		evolutionRank++;
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
}
