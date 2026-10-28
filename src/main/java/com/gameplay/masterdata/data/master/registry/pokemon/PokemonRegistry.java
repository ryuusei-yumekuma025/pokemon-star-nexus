package com.gameplay.masterdata.data.master.registry.pokemon;

import com.gameplay.masterdata.template.pokemon.PokemonSpecies;
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.utils.StringUtils;

import java.util.*;

public class PokemonRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<Integer, PokemonSpecies> POKEMON_SPECIES_BY_POKEDEX_NUMBER_MAP = new LinkedHashMap<>();
	private static final Map<String, PokemonSpecies> POKEMON_SPECIES_BY_CODE_MAP = new HashMap<>();

	private static final Map<String, PokemonForm> POKEMON_FORM_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, PokemonForm> POKEMON_FORM_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PokemonRegistry() {}

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

	public static synchronized void register(PokemonSpecies pokemonSpecies) {
		if (pokemonSpecies == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("PokemonRegistry is frozen. Failed Register : " + pokemonSpecies.getSpeciesCode());
		}

		int pokedexNumber = pokemonSpecies.getPokedexNumber();
		String speciesCode = pokemonSpecies.getSpeciesCode();

		if (!StringUtils.isNullOrBlank(speciesCode)) {
			POKEMON_SPECIES_BY_POKEDEX_NUMBER_MAP.put(pokedexNumber, pokemonSpecies);
			POKEMON_SPECIES_BY_CODE_MAP.put(speciesCode, pokemonSpecies);

			for (PokemonForm pokemonForm : pokemonSpecies.getAllForms()) {
				String formId = speciesCode + "_" + pokemonForm.getPokemonFormId();
				String formCode = pokemonForm.getPokemonFormCode();

				if ("BASE".equalsIgnoreCase(formCode)) {
					formCode = speciesCode;
				} else {
					formCode = speciesCode + "_" + formCode;
				}

				if (!StringUtils.isNullOrBlank(formId, formCode)) {
					POKEMON_FORM_BY_ID_MAP.put(formId, pokemonForm);
					POKEMON_FORM_BY_CODE_MAP.put(formCode, pokemonForm);
				}
			}
		}
	}

	//==================================================================================================================
	//	Species Search
	//==================================================================================================================

	public static PokemonSpecies getPokemonSpeciesByPokedexNumber(int pokedexNumber) {
		PokemonSpecies pokemonSpecies = POKEMON_SPECIES_BY_POKEDEX_NUMBER_MAP.get(pokedexNumber);
		if (pokemonSpecies == null) {
			throw new IllegalArgumentException("Failed Pokemon Species Search : " + pokedexNumber);
		}

		return pokemonSpecies;
	}

	public static PokemonSpecies getPokemonSpeciesByCode(String speciesCode) {
		if (StringUtils.isNullOrBlank(speciesCode)) {
			throw new IllegalArgumentException("Missing Species Code.");
		}

		PokemonSpecies pokemonSpecies = POKEMON_SPECIES_BY_CODE_MAP.get(StringUtils.upperSnakeCase(speciesCode));
		if (pokemonSpecies == null) {
			throw new IllegalArgumentException("Failed Pokemon Species Search : " + speciesCode);
		}

		return pokemonSpecies;
	}

	public static Collection<PokemonSpecies> getAllPokemonSpecies() {
		return Collections.unmodifiableCollection(POKEMON_SPECIES_BY_POKEDEX_NUMBER_MAP.values());
	}

	public static int getNumberOfPokemonSpecies() {
		return POKEMON_SPECIES_BY_POKEDEX_NUMBER_MAP.size();
	}

	//==================================================================================================================
	//	Form Search
	//==================================================================================================================

	public static PokemonForm getPokemonFormById(String formId) {
		if (StringUtils.isNullOrBlank(formId)) {
			throw new IllegalArgumentException("Missing Form Id.");
		}

		PokemonForm pokemonForm = POKEMON_FORM_BY_ID_MAP.get(StringUtils.upperSnakeCase(formId));
		if (pokemonForm == null) {
			throw new IllegalArgumentException("Failed Pokemon Form Search : " + formId);
		}

		return pokemonForm;
	}

	public static PokemonForm getPokemonFormByCode(String formCode) {
		if (StringUtils.isNullOrBlank(formCode)) {
			throw new IllegalArgumentException("Missing Form Code.");
		}

		PokemonForm pokemonForm = POKEMON_FORM_BY_CODE_MAP.get(StringUtils.upperSnakeCase(formCode));
		if (pokemonForm == null) {
			throw new IllegalArgumentException("Failed Pokemon Form Search : " + formCode);
		}

		return pokemonForm;
	}

	public static Collection<PokemonForm> getAllPokemonForms() {
		return Collections.unmodifiableCollection(POKEMON_FORM_BY_ID_MAP.values());
	}

	public static int getNumberOfPokemonForms() {
		return POKEMON_FORM_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("PokemonRegistry is frozen. Cannot Clear Registry.");
		}

		POKEMON_SPECIES_BY_POKEDEX_NUMBER_MAP.clear();
		POKEMON_SPECIES_BY_CODE_MAP.clear();
		POKEMON_FORM_BY_ID_MAP.clear();
		POKEMON_FORM_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
