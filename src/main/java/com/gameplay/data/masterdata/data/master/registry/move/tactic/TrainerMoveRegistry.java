package com.gameplay.data.masterdata.data.master.registry.move.tactic;

import com.gameplay.data.masterdata.data.master.registry.move.MoveRegistry;
import com.gameplay.data.masterdata.template.move.tactic.trainer.TrainerMove;

import java.util.Collection;

public final class TrainerMoveRegistry extends MoveRegistry<String, TrainerMove> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Constructor																										*/

	private static final TrainerMoveRegistry INSTANCE = new TrainerMoveRegistry();

	private TrainerMoveRegistry() {
		super("TrainerMoveRegistry", "TrainerMove", "TM", TrainerMove::getMoveId, TrainerMove::getMoveCode);
	}

	/*------------------------------------------------------------------------------------------------------------------
	2.	Data Registry Mode																								*/

	public static void freeze() {
		INSTANCE.freezeMoveStorage();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Register																										*/

	public static void register(TrainerMove trainerMove) {
		INSTANCE.registerMove(trainerMove);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Search and Getters																								*/

	public static TrainerMove getById(String moveId) {
		return INSTANCE.getMoveById(moveId);
	}

	public static TrainerMove getByCode(String moveCode) {
		return INSTANCE.getMoveByCode(moveCode);
	}

	public static Collection<TrainerMove> getAllTrainerMoves() {
		return INSTANCE.getAllMoves();
	}

	public static int size() {
		return INSTANCE.getMovesSize();
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Clear and Reset																											*/

	public static void clear() {
		INSTANCE.clearMoves();
	}

	public static void reset() {
		INSTANCE.resetMoves();
	}
}
