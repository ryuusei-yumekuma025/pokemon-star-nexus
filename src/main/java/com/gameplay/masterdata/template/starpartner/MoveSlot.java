package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.data.master.registry.move.battle.PokemonMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.battle.ResonanceMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.masterdata.template.move.Move;

public class MoveSlot {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final int moveSlot;
	private final Move move;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public MoveSlot(int moveSlot, String moveClass, String moveCode) {
		this.moveSlot = moveSlot;
		this.move = switch (moveClass) {
			case "POKEMON" -> PokemonMoveRegistry.getPokemonMoveByCode(moveCode);
			case "RESONANCE" -> ResonanceMoveRegistry.getResonanceMoveByCode(moveCode);
			case "ITEM" -> ItemMoveRegistry.getItemMoveByCode(moveCode);
			case "TRAINER" -> TrainerMoveRegistry.getTrainerMoveByCode(moveCode);
			default -> null;
		};
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getMoveSlot() {
		return moveSlot;
	}

	public Move getMove() {
		return move;
	}

	public <M extends Move> M getMove(Class<M> moveClass) {
		return moveClass.cast(getMove());
	}
}
