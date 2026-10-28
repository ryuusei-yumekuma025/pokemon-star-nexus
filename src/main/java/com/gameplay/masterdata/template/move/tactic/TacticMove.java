package com.gameplay.masterdata.template.move.tactic;

import com.gameplay.masterdata.attribute.move.MoveTag;
import com.gameplay.masterdata.attribute.move.MoveTarget;
import com.gameplay.masterdata.template.move.Move;
import com.utils.StringUtils;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public abstract class TacticMove extends Move {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final int tacticPoint;
	private final int bondEnergyCost;
	private final int stardustTuning;
	private final MoveTarget moveTarget;
	private final Set<MoveTag> moveTagSet;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected TacticMove(Builder<?> builder) {
		super(builder);
		this.tacticPoint = builder.tacticPoint;
		this.bondEnergyCost = builder.bondEnergyCost;
		this.stardustTuning = builder.stardustTuning;
		this.moveTarget = builder.moveTarget;
		this.moveTagSet = Collections.unmodifiableSet(EnumSet.copyOf(builder.moveTagSet));
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getTacticPoint() {
		return tacticPoint;
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

	public abstract static class Builder<B extends Builder<B>> extends Move.Builder<B> {
		private int tacticPoint = 1;
		private int bondEnergyCost = 0;
		private int stardustTuning = 0;
		private MoveTarget moveTarget = MoveTarget.SELF;
		private final EnumSet<MoveTag> moveTagSet = EnumSet.noneOf(MoveTag.class);

		protected Builder(String moveId, String moveCode) {
			super(moveId, moveCode);
		}

		public B setData(int tacticPoint, int bondEnergyCost, int stardustTuning) {
			this.tacticPoint = tacticPoint;
			this.bondEnergyCost = bondEnergyCost;
			this.stardustTuning = stardustTuning;
			return self();
		}

		public B setTarget(String moveTarget) {
			MoveTarget target = MoveTarget.fromCode(moveTarget);
			if (target != null) {
				this.moveTarget = target;
			}
			return self();
		}

		public B setTags(List<String> moveTagList) {
			this.moveTagSet.clear();
			if (moveTagList != null) {
				for (String moveTag : moveTagList) {
					MoveTag tag = MoveTag.fromCode(moveTag);
					if (tag != null) {
						this.moveTagSet.add(tag);
					}
				}
			}
			return self();
		}

		@Override
		public abstract TacticMove build();
	}
}
