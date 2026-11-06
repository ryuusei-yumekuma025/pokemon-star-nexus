package com.gameplay.profile.unit.starpartner;

import com.gameplay.masterdata.template.starpartner.StarPartner;

import java.time.Instant;

public class OwnedStarPartner {

	//==================================================================================================================
	//	Field, Properties and Info
	//==================================================================================================================

	private final StarPartner starPartner;
	private final Instant uniteTime;

	private int level;
	private int maxLevelCap;
	private long currentEXP;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public OwnedStarPartner(StarPartner starPartner, Instant uniteTime) {
		this.starPartner = starPartner;
		this.uniteTime = uniteTime;

		this.level = 1;
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
		String date = uniteTime.toString().substring(8, 10);
		String month = uniteTime.toString().substring(5, 7);
		String year = uniteTime.toString().substring(0, 4);
		return date + "/" + month + "/" + year;
	}

	//==================================================================================================================
	//	Level and EXP Management
	//==================================================================================================================

	public int getLevel() {
		return level;
	}

	public long getCurrentEXP() {
		return currentEXP;
	}

	public void gainEXP(long exp) {

	}
}
