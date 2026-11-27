package com.gameplay.data.masterdata.data.master.dto.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.data.master.dto.move.combat.ResonanceMoveDTO;
import com.gameplay.data.masterdata.data.master.registry.move.combat.ResonanceMoveRegistry;
import com.gameplay.data.masterdata.template.move.combat.resonance.ResonanceMove;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpecialMovesDTO(
		@JsonProperty("resonance_moves")
		List<ResonanceMoveDTO> resonanceMoveDTOList
) {
	public SpecialMovesDTO {
		resonanceMoveDTOList = (resonanceMoveDTOList != null) ? List.copyOf(resonanceMoveDTOList) : List.of();
	}

	public void parse(String starUnitId) {
		for (ResonanceMoveDTO resonanceMoveDTO : resonanceMoveDTOList) {
			ResonanceMove resonanceMove = resonanceMoveDTO.create(starUnitId);
			ResonanceMoveRegistry.register(resonanceMove);
		}
	}
}
