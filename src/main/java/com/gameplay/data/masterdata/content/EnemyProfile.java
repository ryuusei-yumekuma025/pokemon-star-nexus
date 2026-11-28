package com.gameplay.data.masterdata.content;

import com.gameplay.battle.entity.unit.profile.UnitProfile;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;

public class EnemyProfile implements UnitProfile {

	private final String profileId;
	private final StarEnemy starEnemy;
	private final int level;
	private final double hpThreshold;

	public EnemyProfile(String stageId, StarEnemy starEnemy, int level, double hpThreshold) {
		this.profileId = "STGENM_" + stageId + starEnemy.getStarUnitId().substring(4);
		this.starEnemy = starEnemy;
		this.level = level;
		this.hpThreshold = hpThreshold;
	}

	public EnemyProfile(String stageId, StarEnemy starEnemy, int level) {
		this(stageId, starEnemy, level, 1.0);
	}

	@Override
	public String getProfileId() {
		return profileId;
	}

	@Override
	public StarEnemy getStarUnit() {
		return starEnemy;
	}

	@Override
	public int getLevel() {
		return level;
	}

	public double getHpThreshold() {
		return hpThreshold;
	}
}
