package com.gameplay.masterdata.data.master.dto.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.utils.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonFormDTO(
		@JsonProperty("id")
		int formId,
		@JsonProperty("code")
		String formCode,
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
		Double height,
		@JsonProperty("weight")
		Double weight,
		@JsonProperty("gender_appearance_difference")
		Boolean genderAppearanceDifference
) {
	public PokemonFormDTO {
		if (formCode == null) {
			formCode = "BASE";
		}
		formCode = StringUtils.upperSnakeCase(formCode);

		if (genderAppearanceDifference == null) {
			genderAppearanceDifference = false;
		}
	}

	private static <T> T inherit(T own, T inherited) {
		return own != null ? own : inherited;
	}

	public PokemonForm create(PokemonSpeciesDTO pokemonSpecies) {
		PokemonForm.Builder builder = new PokemonForm.Builder(pokemonSpecies.pokedexNumber(), formId, formCode)
				.setCategory(inherit(pokemonCategory, pokemonSpecies.pokemonCategory()))
				.setType(inherit(pokemonTypeList, pokemonSpecies.pokemonTypeList()))
				.setEggGroups(inherit(eggGroupList, pokemonSpecies.eggGroupList()))
				.setAppearance(inherit(color, pokemonSpecies.color()), inherit(shape, pokemonSpecies.shape()), genderAppearanceDifference)
				.setDimension(inherit(height, pokemonSpecies.height()), inherit(weight, pokemonSpecies.weight()));

		return builder.build();
	}
}
