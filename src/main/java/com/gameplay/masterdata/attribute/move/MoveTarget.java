package com.gameplay.masterdata.attribute.move;

import com.utils.StringUtils;

public enum MoveTarget {
	SELF(1),

	SINGLE_TARGET(0, 2, 3),
	SINGLE_TARGET_LONG_RANGE(0, 2, 3, 7),
	SELF_OR_SINGLE_ALLY(0, 1, 2),
	SELF_OR_SINGLE_ALLY_LONG_RANGE(0, 1, 2, 7);

	private static final int canAimTarget = 1;
	private static final int canTargetSelf = 1 << 1;
	private static final int canTargetAlly = 1 << 2;
	private static final int canTargetEnemy = 1 << 3;
	private static final int canTargetField = 1 << 4;
	private static final int targetAllEligible = 1 << 5;
	private static final int isAreaOfEffect = 1 << 6;
	private static final int isLongRange = 1 << 7;

	private final int[] flagTagIdArray;

	MoveTarget(int... flagTagIdArray) {
		this.flagTagIdArray = flagTagIdArray;
	}

	public static MoveTarget fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid MoveTarget : " + code);
		}
	}

	public int getFlagTagsValue() {
		int flagTagsValue = 0;

		for (int flagTagId : flagTagIdArray) {
			flagTagsValue |= 1 << flagTagId;
		}

		return flagTagsValue;
	}

	public boolean canAimTarget() {
		return (getFlagTagsValue() & canAimTarget) != 0;
	}

	public boolean canTargetSelf() {
		return (getFlagTagsValue() & canTargetSelf) != 0;
	}

	public boolean canTargetAlly() {
		return (getFlagTagsValue() & canTargetAlly) != 0;
	}

	public boolean canTargetEnemy() {
		return (getFlagTagsValue() & canTargetEnemy) != 0;
	}

	public boolean canTargetField() {
		return (getFlagTagsValue() & canTargetField) != 0;
	}

	public boolean targetAllEligible() {
		return (getFlagTagsValue() & targetAllEligible) != 0;
	}

	public boolean isAreaOfEffect() {
		return (getFlagTagsValue() & isAreaOfEffect) != 0;
	}

	public boolean isLongRange() {
		return (getFlagTagsValue() & isLongRange) != 0;
	}
}
