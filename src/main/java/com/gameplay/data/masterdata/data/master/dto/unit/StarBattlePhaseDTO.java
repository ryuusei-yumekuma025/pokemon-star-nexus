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
		Integer defaultState,
		@JsonProperty("states")
		List<StarBattleStateDTO> starBattleStateDTOList
) {
	public StarBattlePhaseDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Phase Code");
		defaultState = (defaultState != null) ? defaultState : 0;
		starBattleStateDTOList = (starBattleStateDTOList != null) ? List.copyOf(starBattleStateDTOList) : List.of();
	}

	public StarBattlePhase create() {
		StarBattlePhase.Builder builder = new StarBattlePhase.Builder(phase, code, defaultState);

		for (StarBattleStateDTO starBattleStateDTO : starBattleStateDTOList) {
			builder.addState(starBattleStateDTO.create());
		}

		return builder.build();
	}
}
