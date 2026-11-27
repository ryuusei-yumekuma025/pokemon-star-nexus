package com.gameplay.data.masterdata.data.master.dto.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleState;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StarBattleStateDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("code")
		String code,
		@JsonProperty("pokemon_form")
		String pokemonForm,
		@JsonProperty("base_stat")
		BaseStatDTO baseStatDTO,
		@JsonProperty("moveset")
		List<String> moveset
) {
	public StarBattleStateDTO {
		code = (code != null) ? code : "BASE";
		pokemonForm = StringUtils.requireUpperSnakeCase(pokemonForm, "Pokemon Form");
		moveset = (moveset != null) ? List.copyOf(moveset) : List.of();
	}

	public StarBattleState create() {
		StarBattleState.Builder builder = new StarBattleState.Builder(id, code, pokemonForm)
				.setBaseStat(baseStatDTO)
				.setMoveset(moveset);

		return builder.build();
	}
}
