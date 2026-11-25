package com.gameplay.data.masterdata.data.master.loader.pokemon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.data.masterdata.data.master.dto.pokemon.PokemonSpeciesDTO;
import com.gameplay.data.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.data.masterdata.template.pokemon.PokemonSpecies;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class PokemonLoader {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Loader Config																								*/

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/pokemon/generation_";
	private static final String DEX = "data/pokemon/pokedex.json";

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private PokemonLoader() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Loader																											*/

	public static List<List<String>> loadPokedex() {
		try (InputStream inputStream = PokemonLoader.class.getClassLoader().getResourceAsStream(DEX)) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Load Pokedex Failed : " + DEX);
			}

			return MAPPER.readValue(inputStream, new TypeReference<List<List<String>>>() {});
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + DEX, e);
		}
	}

	public static void loadPokemon(String pokemonPath) {
		try (InputStream inputStream = PokemonLoader.class.getClassLoader().getResourceAsStream(pokemonPath)) {
			if (inputStream == null) {
				return;
			}

			// Chỉ để parse file json khi chưa hoàn thiện hết các file thôi
			String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).trim();
			if (json.isEmpty() || json.matches("\\{\\s*}")) {
				return;
			}
			//

			PokemonSpeciesDTO pokemonSpeciesDTO = MAPPER.readValue(json, PokemonSpeciesDTO.class);
			try {
				PokemonSpecies pokemonSpecies = pokemonSpeciesDTO.create();
				PokemonRegistry.register(pokemonSpecies);
			} catch (Exception e) {
				throw new IllegalStateException("Error : " + pokemonPath, e);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + pokemonPath, e);
		}
	}

	public static void loadByGeneration(int generation, List<String> pokedexByGeneration) {
		String pokemonPath = PATH + generation + "/";

		for (String pokemon : pokedexByGeneration) {
			loadPokemon(pokemonPath + pokemon + ".json");
		}
	}

	public static void loadAll() {
		int generation = 1;

		for (List<String> pokedexByGeneration : loadPokedex()) {
			loadByGeneration(generation, pokedexByGeneration);
			generation++;
		}
	}
}
