package com.gameplay.data.masterdata.data.master.registry.move.combat;

import com.gameplay.data.masterdata.data.master.registry.move.MoveRegistry;
import com.gameplay.data.masterdata.template.move.combat.pokemon.PokemonMove;

import java.util.Collection;

public final class PokemonMoveRegistry extends MoveRegistry<Integer, PokemonMove> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Constructor																										*/

	private static final PokemonMoveRegistry INSTANCE = new PokemonMoveRegistry();

	private PokemonMoveRegistry() {
		super("PokemonMoveRegistry", "PokemonMove", "PM", PokemonMove::getMoveIdNo, PokemonMove::getMoveCode);
	}

	/*------------------------------------------------------------------------------------------------------------------
	2.	Data Registry Mode																								*/

	public static void freeze() {
		INSTANCE.freezeMoveStorage();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Register																										*/

	public static void register(PokemonMove pokemonMove) {
		INSTANCE.registerMove(pokemonMove);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Search and Getters																								*/

	public static PokemonMove getById(int moveId) {
		return INSTANCE.getMoveById(moveId);
	}

	public static PokemonMove getByCode(String moveCode) {
		return INSTANCE.getMoveByCode(moveCode);
	}

	public static Collection<PokemonMove> getAllPokemonMoves() {
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
