package com.gameplay.masterdata.data.master.registry.move.battle;

import com.gameplay.masterdata.template.move.battle.pokemon.PokemonMove;
import com.utils.StringUtils;

import java.util.*;

public class PokemonMoveRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<Integer, PokemonMove> POKEMON_MOVE_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, PokemonMove> POKEMON_MOVE_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PokemonMoveRegistry() {}

	//==================================================================================================================
	//	Frozen State Management
	//==================================================================================================================

	private static volatile boolean frozen = false;

	public static synchronized void freeze() {
		frozen = true;
	}

	//==================================================================================================================
	//	Register
	//==================================================================================================================

	public static synchronized void register(PokemonMove pokemonMove) {
		if (pokemonMove == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("PokemonMoveRegistry is frozen. Failed Register : " + pokemonMove.getMoveCode());
		}

		int moveId = pokemonMove.getMoveIdNo();
		String moveCode = StringUtils.upperSnakeCase(pokemonMove.getMoveCode());

		if (!StringUtils.isNullOrBlank(moveCode)) {
			POKEMON_MOVE_BY_ID_MAP.put(moveId, pokemonMove);
			POKEMON_MOVE_BY_CODE_MAP.put(moveCode, pokemonMove);
		}
	}

	//==================================================================================================================
	//	Search
	//==================================================================================================================

	public static PokemonMove getPokemonMoveById(int moveId) {
		PokemonMove pokemonMove = POKEMON_MOVE_BY_ID_MAP.get(moveId);
		if (pokemonMove == null) {
			throw new IllegalArgumentException("Failed Pokemon Move Search : " + moveId);
		}

		return pokemonMove;
	}

	public static PokemonMove getPokemonMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Move Code.");
		}

		PokemonMove pokemonMove = POKEMON_MOVE_BY_CODE_MAP.get(StringUtils.upperSnakeCase(moveCode));
		if (pokemonMove == null) {
			throw new IllegalArgumentException("Failed Pokemon Move Search : " + moveCode);
		}

		return pokemonMove;
	}

	public static Collection<PokemonMove> getAllPokemonMoves() {
		return Collections.unmodifiableCollection(POKEMON_MOVE_BY_ID_MAP.values());
	}

	public static int getNumberOfPokemonMoves() {
		return POKEMON_MOVE_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("PokemonMoveRegistry is frozen. Cannot Clear Registry.");
		}

		POKEMON_MOVE_BY_ID_MAP.clear();
		POKEMON_MOVE_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
