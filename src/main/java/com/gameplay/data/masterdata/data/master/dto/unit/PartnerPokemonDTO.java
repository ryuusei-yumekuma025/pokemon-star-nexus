package com.gameplay.data.masterdata.data.master.dto.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.unit.PartnerPokemon;
import com.utils.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PartnerPokemonDTO(
		@JsonProperty("pokemon")
		String partnerPokemon,
		@JsonProperty("gender")
		String gender,
		@JsonProperty("is_shiny")
		Boolean isShiny
) {
	public PartnerPokemonDTO {
		partnerPokemon = StringUtils.requireUpperSnakeCase(partnerPokemon, "Partner Pokemon");
		gender = (gender != null) ? gender : "GENDERLESS";
		isShiny = isShiny != null && isShiny;
	}

	public PartnerPokemon create() {
		return new PartnerPokemon.Builder(partnerPokemon, gender, isShiny)
				.build();
	}
}
