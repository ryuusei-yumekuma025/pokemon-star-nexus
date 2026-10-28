package com.gameplay.masterdata.data.master.dto.move.starnexus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gameplay.masterdata.data.master.dto.move.battle.PokemonMoveDTO;
import com.gameplay.masterdata.template.move.starnexus.StarNexusMove;
import com.gameplay.masterdata.template.move.starnexus.starmode.mode.ConstellationMode;
import com.gameplay.masterdata.template.move.starnexus.starmode.mode.NebulaMode;
import com.utils.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StarNexusMoveDTO(
		@JsonProperty("id")
		int moveId,
		@JsonProperty("code")
		String moveCode,
		@JsonProperty("prototype")
		String movePrototype,
		@JsonProperty("base_power")
		@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Integer> basePower,
		@JsonProperty("target")
		@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<String> moveTarget,
		@JsonProperty("tags")
		@JsonDeserialize(using = PokemonMoveDTO.TagsDeserializer.class)
		List<List<String>> moveTagList
) {
	private static <T> List<T> parseList(List<T> src, T defaultValue, String name) {
		List<T> list = (src != null) ? List.copyOf(src) : List.of(defaultValue);
		return switch (list.size()) {
			case 1 -> List.of(list.getFirst(), list.getFirst());
			case 2 -> List.copyOf(list);
			default -> throw new IllegalArgumentException("Size of " + name + " must be 1 or 2 but was " + list.size());
		};
	}

	public StarNexusMoveDTO {
		Objects.requireNonNull(moveCode, "Missing Move Code.");
		moveCode = StringUtils.upperSnakeCase(moveCode);
		Objects.requireNonNull(movePrototype, "Missing Move Prototype.");

		basePower = parseList(basePower, -1, "Base Power");
		moveTarget = parseList(moveTarget, "SELF", "Move Target");
		moveTagList = parseList(moveTagList, List.of(), "Move Tags");
	}

	public StarNexusMove create(String starPartnerId) {
		NebulaMode.Builder nebulaModeBuilder = new NebulaMode.Builder()
				.setData(basePower.getFirst())
				.setTarget(moveTarget.getFirst())
				.setTags(moveTagList.getFirst());

		ConstellationMode.Builder constellationModeBuilder = new ConstellationMode.Builder()
				.setData(basePower.getLast())
				.setTarget(moveTarget.getLast())
				.setTags(moveTagList.getLast());

		StarNexusMove.Builder builder = new StarNexusMove.Builder(starPartnerId, moveId, moveCode, movePrototype)
				.setMode(nebulaModeBuilder.build(), constellationModeBuilder.build());

		return builder.build();
	}
}
