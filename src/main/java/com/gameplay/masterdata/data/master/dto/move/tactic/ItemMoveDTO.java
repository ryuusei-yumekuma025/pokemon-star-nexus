package com.gameplay.masterdata.data.master.dto.move.tactic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.move.tactic.item.ItemMove;
import com.utils.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemMoveDTO(
		@JsonProperty("id")
		int moveId,
		@JsonProperty("code")
		String moveCode,
		@JsonProperty("tactic_point")
		int tacticPoint,
		@JsonProperty("bond_energy_cost")
		int bondEnergyCost,
		@JsonProperty("stardust_tuning")
		int stardustTuning,
		@JsonProperty("target")
		String moveTarget,
		@JsonProperty("tags")
		List<String> moveTagList
) {
	public ItemMoveDTO {
		Objects.requireNonNull(moveCode, "Missing Move Code.");
		moveCode = StringUtils.upperSnakeCase(moveCode);
		Objects.requireNonNull(moveTarget, "Missing Move Target.");
		moveTagList = (moveTagList != null) ? List.copyOf(moveTagList) : List.of();
	}

	public ItemMove create() {
		ItemMove.Builder builder = new ItemMove.Builder(moveId, moveCode)
				.setData(tacticPoint, bondEnergyCost, stardustTuning)
				.setTarget(moveTarget)
				.setTags(moveTagList);

		return builder.build();
	}
}
