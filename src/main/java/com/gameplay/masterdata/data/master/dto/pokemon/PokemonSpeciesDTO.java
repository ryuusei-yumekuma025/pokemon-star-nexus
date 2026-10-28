package com.gameplay.masterdata.data.master.dto.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.pokemon.PokemonSpecies;
import com.utils.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonSpeciesDTO(
		@JsonProperty("pokedex_number")
		int pokedexNumber,
		@JsonProperty("species")
		String speciesCode,
		@JsonProperty("category")
		String pokemonCategory,
		@JsonProperty("types")
		List<String> pokemonTypeList,
		@JsonProperty("egg_groups")
		List<String> eggGroupList,
		@JsonProperty("color")
		String color,
		@JsonProperty("shape")
		String shape,
		@JsonProperty("height")
		double height,
		@JsonProperty("weight")
		double weight,
		@JsonProperty("forms")
		List<PokemonFormDTO> pokemonFormDTOList
) {
	public PokemonSpeciesDTO {
		Objects.requireNonNull(speciesCode, "Missing Pokemon Species.");
		speciesCode = StringUtils.upperSnakeCase(speciesCode);
		Objects.requireNonNull(pokemonCategory, "Missing Pokemon Category.");
		pokemonCategory = StringUtils.upperSnakeCase(pokemonCategory);
		pokemonTypeList = (pokemonTypeList != null) ? List.copyOf(pokemonTypeList) : List.of("TYPELESS");
		eggGroupList = (eggGroupList != null) ? List.copyOf(eggGroupList) : List.of();
		Objects.requireNonNull(color, "Missing Body Color.");
		Objects.requireNonNull(shape, "Missing Body Shape.");
		pokemonFormDTOList = (pokemonFormDTOList != null) ? List.copyOf(pokemonFormDTOList) : List.of();
	}

	public PokemonSpecies create() {
		PokemonSpecies.Builder builder = new PokemonSpecies.Builder(pokedexNumber, speciesCode);

		for (PokemonFormDTO pokemonFormDTO : pokemonFormDTOList) {
			builder.addPokemonForm(pokemonFormDTO.create(this));
		}

		return builder.build();
	}
}
