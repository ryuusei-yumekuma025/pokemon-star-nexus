package com.gameplay.data.masterdata.data.master.registry.pokemon;

import com.gameplay.data.masterdata.data.master.registry.Registry;
import com.gameplay.data.masterdata.template.pokemon.PokemonSpecies;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;

import java.util.Collection;

public final class PokemonRegistry {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Registry																									*/

	private static final Registry<Integer, PokemonSpecies> SPECIES_REGISTRY = new Registry<>(
			"PokemonSpeciesRegistry", "PokemonSpecies",
			PokemonSpecies::getPokedexNumber, PokemonSpecies::getSpeciesCode);

	private static final Registry<String, PokemonForm> FORM_REGISTRY = new Registry<>(
			"PokemonFormRegistry", "PokemonForm",
			form -> String.format("%08d%04d", form.getPokedexNumber(), form.getPokemonFormId()),
			PokemonForm::getPokemonFormFullCode);

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private PokemonRegistry() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Data Registry Mode																								*/

	public static synchronized void freeze() {
		SPECIES_REGISTRY.freeze();
		FORM_REGISTRY.freeze();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Register																										*/

	public static synchronized void register(PokemonSpecies pokemonSpecies) {
		if (pokemonSpecies == null) {
			return;
		}

		SPECIES_REGISTRY.register(pokemonSpecies);

		Collection<PokemonForm> pokemonFormCollection = pokemonSpecies.getAllForms();
		if (pokemonFormCollection == null) {
			return;
		}

		for (PokemonForm pokemonForm : pokemonFormCollection) {
			FORM_REGISTRY.register(pokemonForm);
		}
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Species Search and Getters																						*/

	public static PokemonSpecies getSpeciesByPokedexNumber(int pokedexNumber) {
		return SPECIES_REGISTRY.getById(pokedexNumber);
	}

	public static PokemonSpecies getSpeciesByCode(String code) {
		return SPECIES_REGISTRY.getByCode(code);
	}

	public static Collection<PokemonSpecies> getAllPokemonSpecies() {
		return SPECIES_REGISTRY.getAll();
	}

	public static int getNumberOfPokemonSpecies() {
		return SPECIES_REGISTRY.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Form Search and Getters																							*/

	public static PokemonForm getFormById(String id) {
		return FORM_REGISTRY.getById(id);
	}

	public static PokemonForm getFormByPokedexNumberAndId(int pokedexNumber, int formId) {
		return FORM_REGISTRY.getById(String.format("%08d%04d", pokedexNumber, formId));
	}

	public static PokemonForm getFormByCode(String code) {
		return FORM_REGISTRY.getByCode(code);
	}

	public static Collection<PokemonForm> getAllPokemonForms() {
		return FORM_REGISTRY.getAll();
	}

	public static int getNumberOfPokemonForms() {
		return FORM_REGISTRY.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	7.	Clear and Reset																									*/

	public static synchronized void clear() {
		SPECIES_REGISTRY.clear();
		FORM_REGISTRY.clear();
	}

	public static synchronized void reset() {
		SPECIES_REGISTRY.reset();
		FORM_REGISTRY.reset();
	}
}
