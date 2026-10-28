package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.attribute.pokemon.gender.PokemonGender;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.template.pokemon.PokemonSpecies;

public class PartnerPokemon {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final PokemonSpecies pokemonSpecies;
	private final PokemonGender gender;
	private final boolean isShiny;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PartnerPokemon(Builder builder) {
		this.pokemonSpecies = builder.pokemonSpecies;
		this.gender = builder.gender;
		this.isShiny = builder.isShiny;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public PokemonSpecies getPokemonSpecies() {
		return pokemonSpecies;
	}

	public PokemonGender getGender() {
		return gender;
	}

	public boolean isShiny() {
		return isShiny;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final PokemonSpecies pokemonSpecies;
		private final PokemonGender gender;
		private final boolean isShiny;

		public Builder(String pokemonSpecies, int gender, boolean isShiny) {
			this.pokemonSpecies = PokemonRegistry.getPokemonSpeciesByCode(pokemonSpecies);
			this.gender = PokemonGender.fromId(gender);
			this.isShiny = isShiny;
		}

		public PartnerPokemon build() {
			return new PartnerPokemon(this);
		}
	}
}
