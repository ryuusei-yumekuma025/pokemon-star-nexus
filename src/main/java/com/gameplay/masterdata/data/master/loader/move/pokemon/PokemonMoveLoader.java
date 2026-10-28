package com.gameplay.masterdata.data.master.loader.move.pokemon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.masterdata.data.master.dto.move.battle.PokemonMoveDTO;
import com.gameplay.masterdata.data.master.registry.move.battle.PokemonMoveRegistry;
import com.gameplay.masterdata.template.move.battle.pokemon.PokemonMove;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public class PokemonMoveLoader {

	//==================================================================================================================
	//	Configuration
	//==================================================================================================================

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String DEX_PATH = "data/move/pokemon/series-";

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PokemonMoveLoader() {}

	//==================================================================================================================
	//	Loaders
	//==================================================================================================================

	private static void loadBySeries(int series) {
		String fullSeriesPath = DEX_PATH + series + ".json";

		try (InputStream inputStream = PokemonMoveLoader.class.getClassLoader().getResourceAsStream(fullSeriesPath)) {
			if (inputStream == null) {
				return;
			}

			List<PokemonMoveDTO> pokemonMoveDTOList = MAPPER.readValue(inputStream, new TypeReference<List<PokemonMoveDTO>>() {});

			for (PokemonMoveDTO pokemonMoveDTO : pokemonMoveDTOList) {
				PokemonMove pokemonMove = pokemonMoveDTO.create();
				PokemonMoveRegistry.register(pokemonMove);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + fullSeriesPath, e);
		}
	}

	public static void loadAll() {
		for (int series = 1; series <= 9; ++series) {
			loadBySeries(series);
		}
	}
}
