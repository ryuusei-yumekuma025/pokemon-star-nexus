package com.gameplay.masterdata.data.master.dto.starpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.starpartner.PartnerState;
import com.utils.StringUtils;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PartnerStateDTO(
		@JsonProperty("id")
		int stateId,
		@JsonProperty("code")
		String stateCode,
		@JsonProperty("pokemon_form")
		String pokemonForm,
		@JsonProperty("moveset")
		Map<Integer, MoveSlotDTO> moveSlotDTOMap,
		@JsonProperty("star_nexus_move")
		String starNexusMove
) {
	public PartnerStateDTO {
		Objects.requireNonNull(stateCode, "Missing State Code.");
		stateCode = StringUtils.upperSnakeCase(stateCode);
		Objects.requireNonNull(pokemonForm, "Missing Partner Pokemon Form.");
		moveSlotDTOMap = (moveSlotDTOMap != null) ? Map.copyOf(moveSlotDTOMap) : Map.of();
		Objects.requireNonNull(starNexusMove, "Missing Star Nexus Move.");
	}

	public PartnerState create() {
		PartnerState.Builder builder = new PartnerState.Builder(stateId, stateCode)
				.setPartnerPokemonForm(pokemonForm)
				.setStarNexusMove(starNexusMove);

		moveSlotDTOMap.forEach(builder::addMoveSlot);

		return builder.build();
	}
}
