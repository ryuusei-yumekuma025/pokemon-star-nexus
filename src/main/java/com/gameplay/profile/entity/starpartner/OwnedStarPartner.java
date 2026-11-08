package com.gameplay.profile.entity.starpartner;

import com.gameplay.masterdata.template.starpartner.StarPartner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OwnedStarPartner {

	//==================================================================================================================
	//	Field, Properties and Info
	//==================================================================================================================

	private static final int[] LEVEL_CAP_BY_STAR_RESONANCE_RANK = {50, 80, 100, 120, 140, 150};
	private static final int MAX_NON_EX_STAR_RESONANCE_RANK = LEVEL_CAP_BY_STAR_RESONANCE_RANK.length - 1;
	private static final int MAX_STAR_RESONANCE_RANK = MAX_NON_EX_STAR_RESONANCE_RANK + 50;

	private final StarPartner starPartner;
	private final Instant uniteTime;

	private int level;
	private int starResonanceRank;
	private long currentEXP;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public OwnedStarPartner(StarPartner starPartner, Instant uniteTime) {
		this.starPartner = starPartner;
		this.uniteTime = uniteTime;

		this.level = 1;
		this.starResonanceRank = 0;
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
		if (starResonanceRank < 0 || starResonanceRank > MAX_STAR_RESONANCE_RANK) {
			return -1;
		}
		if (starResonanceRank < MAX_NON_EX_STAR_RESONANCE_RANK) {
			return LEVEL_CAP_BY_STAR_RESONANCE_RANK[starResonanceRank];
		}
		return LEVEL_CAP_BY_STAR_RESONANCE_RANK[MAX_NON_EX_STAR_RESONANCE_RANK] + (starResonanceRank - MAX_NON_EX_STAR_RESONANCE_RANK);
	}

	public void unlockLevelCap() {
		if (starResonanceRank >= MAX_STAR_RESONANCE_RANK) {
			return;
		}

		starResonanceRank++;
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
