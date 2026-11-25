package com.gameplay.data.masterdata.content;

import com.gameplay.battle.entity.unit.instance.UnitInstance;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;

public class EnemyInstance implements UnitInstance {

	private final String instanceId;
	private final StarEnemy starEnemy;
	private final int level;
	private final double hpThreshold;

	public EnemyInstance(String stageId, StarEnemy starEnemy, int level, double hpThreshold) {
		this.instanceId = "STGENM_" + stageId + starEnemy.getStarUnitId().substring(4);
		this.starEnemy = starEnemy;
		this.level = level;
		this.hpThreshold = hpThreshold;
	}

	public EnemyInstance(String stageId, StarEnemy starEnemy, int level) {
		this(stageId, starEnemy, level, 1.0);
	}

	@Override
	public String getInstanceId() {
		return instanceId;
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
