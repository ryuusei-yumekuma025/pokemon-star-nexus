package com.gameplay.battle.entity.unit.profile;

import com.gameplay.data.masterdata.template.unit.StarUnit;

public interface UnitProfile {
	StarUnit<?, ?> getStarUnit();
	String getProfileId();
	int getLevel();
}
