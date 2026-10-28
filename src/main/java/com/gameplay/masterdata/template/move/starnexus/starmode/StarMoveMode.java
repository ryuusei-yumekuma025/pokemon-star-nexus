package com.gameplay.masterdata.template.move.starnexus.starmode;

import com.gameplay.masterdata.attribute.move.MoveTag;
import com.gameplay.masterdata.attribute.move.MoveTarget;
import com.gameplay.masterdata.template.move.battle.MoveMasteryRank;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public abstract class StarMoveMode {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final int basePower;
	private final MoveTarget moveTarget;
	private final Set<MoveTag> moveTagSet;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected StarMoveMode(Builder<?> builder) {
		this.basePower = builder.basePower;
		this.moveTarget = builder.moveTarget;
		this.moveTagSet = Collections.unmodifiableSet(EnumSet.copyOf(builder.moveTagSet));
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getBasePower() {
		return basePower;
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

	public abstract static class Builder<B extends Builder<B>> {
		private int basePower;
		private MoveTarget moveTarget;
		private final EnumSet<MoveTag> moveTagSet = EnumSet.noneOf(MoveTag.class);

		@SuppressWarnings("unchecked")
		protected B self() {
			return (B) this;
		}

		public B setData(int basePower) {
			this.basePower = basePower;
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

		public abstract StarMoveMode build();
	}
}
