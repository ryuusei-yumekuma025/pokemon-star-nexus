package com.gameplay.masterdata.template.move.battle;

import com.gameplay.masterdata.attribute.move.MoveCategory;
import com.gameplay.masterdata.attribute.move.MoveTag;
import com.gameplay.masterdata.attribute.move.MoveTarget;
import com.gameplay.masterdata.attribute.move.MoveTier;
import com.gameplay.masterdata.attribute.type.PokemonType;
import com.gameplay.masterdata.template.move.Move;
import com.gameplay.masterdata.template.move.battle.pokemon.PokemonMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BattleMove extends Move {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final PokemonType moveType;
	private final MoveCategory moveCategory;
	private final MoveTier moveTier;
	private final List<MoveMasteryRank> moveMasteryRankList;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected BattleMove(Builder<?> builder) {
		super(builder);
		this.moveType = builder.moveType;
		this.moveCategory = builder.moveCategory;
		this.moveTier = builder.moveTier;
		this.moveMasteryRankList = List.copyOf(builder.moveMasteryRankList);
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public PokemonType getMoveType() {
		return moveType;
	}

	public MoveCategory getMoveCategory() {
		return moveCategory;
	}

	public MoveTier getMoveTier() {
		return moveTier;
	}

	public List<MoveMasteryRank> getMoveMasteryRankList() {
		return moveMasteryRankList;
	}

	public MoveMasteryRank getMoveMasteryRank(int moveMasteryRank) {
		if (moveMasteryRank < 0 || moveMasteryRank >= moveMasteryRankList.size()) {
			throw new IllegalArgumentException("Move Mastery Rank is out of range : " + moveMasteryRank);
		}
		return moveMasteryRankList.get(moveMasteryRank);
	}

	//==================================================================================================================
	//	Move Mastery Rank Getters
	//==================================================================================================================

	public int getBasePower(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getBasePower();
	}

	public double getAccuracy(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getAccuracy();
	}

	public double getActionSpeed(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getActionSpeed();
	}

	public int getBondEnergyCost(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getBondEnergyCost();
	}

	public int getStardustTuning(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getStardustTuning();
	}

	public MoveTarget getMoveTarget(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getMoveTarget();
	}

	public Set<MoveTag> getMoveTagSet(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getMoveTagSet();
	}

	public boolean hasTag(int moveMasteryRank, MoveTag moveTag) {
		return getMoveMasteryRank(moveMasteryRank).hasTag(moveTag);
	}

	//==================================================================================================================
	//	Base Move Getters
	//==================================================================================================================

	public int getBasePower() {
		return getBasePower(0);
	}

	public double getAccuracy() {
		return getAccuracy(0);
	}

	public double getActionSpeed() {
		return getActionSpeed(0);
	}

	public int getBondEnergyCost() {
		return getBondEnergyCost(0);
	}

	public int getStardustTuning() {
		return getStardustTuning(0);
	}

	public MoveTarget getMoveTarget() {
		return getMoveTarget(0);
	}

	public Set<MoveTag> getMoveTagSet() {
		return getMoveTagSet(0);
	}

	public boolean hasTag(MoveTag moveTag) {
		return hasTag(0, moveTag);
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public abstract static class Builder<B extends Builder<B>> extends Move.Builder<B> {
		private PokemonType moveType = PokemonType.TYPELESS;
		private MoveCategory moveCategory = MoveCategory.PHYSICAL;
		private MoveTier moveTier = MoveTier.LOW;
		private final List<MoveMasteryRank> moveMasteryRankList = new ArrayList<>();

		protected Builder(String moveId, String moveCode) {
			super(moveId, moveCode);
		}

		protected Builder(String moveId, String moveCode, PokemonMove movePrototype) {
			super(moveId, moveCode);
			this.moveType = movePrototype.getMoveType();
			this.moveCategory = movePrototype.getMoveCategory();
			this.moveTier = movePrototype.getMoveTier();
		}

		public B setAttribute(String moveType, String moveCategory, int moveTier) {
			PokemonType type = PokemonType.fromCode(moveType);
			MoveCategory category = MoveCategory.fromCode(moveCategory);
			MoveTier tier = MoveTier.fromId(moveTier);

			if (type != null && category != null && tier != null) {
				this.moveType = type;
				this.moveCategory = category;
				this.moveTier = tier;
			}

			return self();
		}

		public void addMasteryRank(MoveMasteryRank moveMasteryRank) {
			if (moveMasteryRank != null) {
				this.moveMasteryRankList.add(moveMasteryRank);
			}
		}

		@Override
		public abstract BattleMove build();
	}
}
