package com.gameplay.battle.entity.unit.instance;

import com.gameplay.data.masterdata.template.unit.StarUnit;

public interface UnitInstance {
	StarUnit<?, ?> getStarUnit();
	String getInstanceId();
	int getLevel();
}
