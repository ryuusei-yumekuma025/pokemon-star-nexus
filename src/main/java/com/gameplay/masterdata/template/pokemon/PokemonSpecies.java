package com.gameplay.masterdata.template.pokemon;

import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.utils.StringUtils;

import java.util.*;

public class PokemonSpecies {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final int pokedexNumber;
	private final String speciesCode;
	private final Map<Integer, PokemonForm> pokemonFormByIdMap;
	private final Map<String, PokemonForm> pokemonFormByCodeMap;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PokemonSpecies(Builder builder) {
		this.pokedexNumber = builder.pokedexNumber;
		this.speciesCode = builder.speciesCode;
		this.pokemonFormByIdMap = Collections.unmodifiableMap(new LinkedHashMap<>(builder.pokemonFormByIdMap));
		this.pokemonFormByCodeMap = Map.copyOf(builder.pokemonFormByCodeMap);
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getPokedexNumber() {
		return pokedexNumber;
	}

	public String getSpeciesCode() {
		return speciesCode;
	}

	public PokemonForm getPokemonFormById(int formId) {
		return pokemonFormByIdMap.get(formId);
	}

	public PokemonForm getPokemonFormByCode(String formCode) {
		if (StringUtils.isNullOrBlank(formCode)) {
			return null;
		}
		return pokemonFormByCodeMap.get(StringUtils.upperSnakeCase(formCode));
	}

	public Collection<PokemonForm> getAllForms() {
		return Collections.unmodifiableCollection(pokemonFormByIdMap.values());
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final int pokedexNumber;
		private final String speciesCode;
		private final Map<Integer, PokemonForm> pokemonFormByIdMap = new LinkedHashMap<>();
		private final Map<String, PokemonForm> pokemonFormByCodeMap = new HashMap<>();

		public Builder(int pokedexNumber, String speciesCode) {
			this.pokedexNumber = pokedexNumber;
			this.speciesCode = StringUtils.upperSnakeCase(speciesCode);
		}

		public void addPokemonForm(PokemonForm pokemonForm) {
			if (pokemonForm != null) {
				int formId = pokemonForm.getPokemonFormId();
				String formCode = StringUtils.upperSnakeCase(pokemonForm.getPokemonFormCode());

				if (!StringUtils.isNullOrBlank(formCode)) {
					this.pokemonFormByIdMap.put(formId, pokemonForm);
					this.pokemonFormByCodeMap.put(formCode, pokemonForm);
				}
			}
		}

		public PokemonSpecies build() {
			return new PokemonSpecies(this);
		}
	}
}
