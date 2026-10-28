package com.gameplay.masterdata.data.master.dto.starpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.starpartner.PartnerPokemon;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PartnerPokemonDTO(
		@JsonProperty("species")
		String pokemonSpecies,
		@JsonProperty("gender")
		int gender,
		@JsonProperty("is_shiny")
		boolean isShiny
) {
	public PartnerPokemonDTO {
		Objects.requireNonNull(pokemonSpecies, "Missing Pokemon Species.");
	}

	public PartnerPokemon create() {
		PartnerPokemon.Builder builder = new PartnerPokemon.Builder(pokemonSpecies, gender, isShiny);

		return builder.build();
	}
}
