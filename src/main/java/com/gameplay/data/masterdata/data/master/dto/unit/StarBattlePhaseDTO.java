package com.gameplay.data.masterdata.data.master.dto.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.unit.phase.StarBattlePhase;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StarBattlePhaseDTO(
		@JsonProperty("phase")
		int phase,
		@JsonProperty("code")
		String code,
		@JsonProperty("default")
		Integer defaultMode,
		@JsonProperty("modes")
		List<StarBattleModeDTO> starBattleModeDTOList
) {
	public StarBattlePhaseDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Phase Code");
		defaultMode = (defaultMode != null) ? defaultMode : 0;
		starBattleModeDTOList = (starBattleModeDTOList != null) ? List.copyOf(starBattleModeDTOList) : List.of();
	}

	public StarBattlePhase create() {
		StarBattlePhase.Builder builder = new StarBattlePhase.Builder(phase, code, defaultMode);

		for (StarBattleModeDTO starBattleModeDTO : starBattleModeDTOList) {
			builder.addMode(starBattleModeDTO.create());
		}

		return builder.build();
	}
}
