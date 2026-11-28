package com.gameplay.data.masterdata.data.master.registry.move.combat;

import com.gameplay.data.masterdata.data.master.registry.move.MoveRegistry;
import com.gameplay.data.masterdata.template.move.combat.resonance.ResonanceMove;

import java.util.Collection;

public final class ResonanceMoveRegistry extends MoveRegistry<String, ResonanceMove> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Constructor																										*/

	private static final ResonanceMoveRegistry INSTANCE = new ResonanceMoveRegistry();

	private ResonanceMoveRegistry() {
		super("ResonanceMoveRegistry", "ResonanceMove", "RM", ResonanceMove::getMoveId, ResonanceMove::getMoveCode);
	}

	/*------------------------------------------------------------------------------------------------------------------
	2.	Data Registry Mode																								*/

	public static void freeze() {
		INSTANCE.freezeMoveStorage();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Register																										*/

	public static void register(ResonanceMove resonanceMove) {
		INSTANCE.registerMove(resonanceMove);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Search and Getters																								*/

	public static ResonanceMove getById(String moveId) {
		return INSTANCE.getMoveById(moveId);
	}

	public static ResonanceMove getByCode(String moveCode) {
		return INSTANCE.getMoveByCode(moveCode);
	}

	public static Collection<ResonanceMove> getAllResonanceMoves() {
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
