package com.gameplay.masterdata.data.master.loader.pokemon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.masterdata.data.master.dto.pokemon.PokemonSpeciesDTO;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.template.pokemon.PokemonSpecies;
import com.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public class PokemonLoader {

	//==================================================================================================================
	//	Configuration
	//==================================================================================================================

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/pokemon/";
	private static final String DEX_PATH = PATH + "pokedex.json";
	private static final String GEN_PATH = PATH + "generation_";

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PokemonLoader() {}

	//==================================================================================================================
	//	Loaders
	//==================================================================================================================

	private static void loadPokemon(String pokemonPath) {
		try (InputStream inputStream = PokemonLoader.class.getClassLoader().getResourceAsStream(pokemonPath)) {
			if (inputStream == null) {
				return;
			}

			PokemonSpeciesDTO pokemonSpeciesDTO = MAPPER.readValue(inputStream, PokemonSpeciesDTO.class);
			PokemonSpecies pokemonSpecies = pokemonSpeciesDTO.create();
			PokemonRegistry.register(pokemonSpecies);
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + pokemonPath, e);
		}
	}

	private static void loadByGeneration(int generation, List<String> pokedexByGeneration) {
		if (pokedexByGeneration == null || generation <= 0) {
			return;
		}

		String basePath = GEN_PATH + generation + "/";

		for (String pokemon : pokedexByGeneration) {
			if (!StringUtils.isNullOrBlank(pokemon)) {
				loadPokemon(basePath + pokemon + ".json");
			}
		}
	}

	public static void loadAll() {
		try (InputStream inputStream = PokemonLoader.class.getClassLoader().getResourceAsStream(DEX_PATH)) {
			if (inputStream == null) {
				return;
			}

			List<List<String>> pokedexByGenerationList = MAPPER.readValue(inputStream, new TypeReference<List<List<String>>>() {});

			int generation = 1;

			for (List<String> pokedexByGeneration : pokedexByGenerationList) {
				loadByGeneration(generation++, pokedexByGeneration);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + DEX_PATH, e);
		}
	}
}
