package com.gameplay.masterdata.template.move.battle;

import com.gameplay.masterdata.attribute.move.MoveTag;
import com.gameplay.masterdata.attribute.move.MoveTarget;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class MoveMasteryRank {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	public static final int MAX_MASTERY_RANK = 2;
	private final int moveMasteryRank;
	private final int basePower;
	private final double accuracy;
	private final double actionSpeed;
	private final int bondEnergyCost;
	private final int stardustTuning;
	private final MoveTarget moveTarget;
	private final Set<MoveTag> moveTagSet;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private MoveMasteryRank(Builder builder) {
		this.moveMasteryRank = builder.moveMasteryRank;
		this.basePower = builder.basePower;
		this.accuracy = builder.accuracy;
		this.actionSpeed = builder.actionSpeed;
		this.bondEnergyCost = builder.bondEnergyCost;
		this.stardustTuning = builder.stardustTuning;
		this.moveTarget = builder.moveTarget;
		this.moveTagSet = Collections.unmodifiableSet(EnumSet.copyOf(builder.moveTagSet));
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getMoveMasteryRank() {
		return moveMasteryRank;
	}

	public int getBasePower() {
		return basePower;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getActionSpeed() {
		return actionSpeed;
	}

	public int getBondEnergyCost() {
		return bondEnergyCost;
	}

	public int getStardustTuning() {
		return stardustTuning;
	}

	public MoveTarget getMoveTarget() {
		return moveTarget;
	}

	public Set<MoveTag> getMoveTagSet() {
		return moveTagSet;
	}

	public boolean hasTag(MoveTag moveTag) {
		return moveTagSet.contains(moveTag);
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final int moveMasteryRank;
		private int basePower = -1;
		private double accuracy = 100.0;
		private double actionSpeed = 0.0;
		private int bondEnergyCost = 0;
		private int stardustTuning = 0;
		private MoveTarget moveTarget = MoveTarget.SELF;
		private final EnumSet<MoveTag> moveTagSet = EnumSet.noneOf(MoveTag.class);

		public Builder(int moveMasteryRank) {
			if (moveMasteryRank < 0 || moveMasteryRank > MAX_MASTERY_RANK) {
				throw new IllegalArgumentException("Move Mastery Rank is out of range.");
			}
			this.moveMasteryRank = moveMasteryRank;
		}

		public Builder setData(int basePower, double accuracy, double actionSpeed, int bondEnergyCost, int stardustTuning) {
			this.basePower = basePower;
			this.accuracy = accuracy;
			this.actionSpeed = actionSpeed;
			this.bondEnergyCost = bondEnergyCost;
			this.stardustTuning = stardustTuning;
			return this;
		}

		public Builder setTarget(String moveTarget) {
			MoveTarget target = MoveTarget.fromCode(moveTarget);
			if (target != null) {
				this.moveTarget = target;
			}
			return this;
		}

		public Builder setTags(List<String> moveTagList) {
			this.moveTagSet.clear();
			if (moveTagList != null) {
				for (String moveTag : moveTagList) {
					MoveTag tag = MoveTag.fromCode(moveTag);
					if (tag != null) {
						this.moveTagSet.add(tag);
					}
				}
			}
			return this;
		}

		public MoveMasteryRank build() {
			return new MoveMasteryRank(this);
		}
	}
}
