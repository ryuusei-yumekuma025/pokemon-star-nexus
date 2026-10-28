package com.gameplay.masterdata.data.master.dto.move.battle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gameplay.masterdata.template.move.battle.MoveMasteryRank;
import com.gameplay.masterdata.template.move.battle.resonance.ResonanceMove;
import com.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResonanceMoveDTO(
		@JsonProperty("id")
		int moveId,
		@JsonProperty("code")
		String moveCode,
		@JsonProperty("prototype")
		String movePrototype,
		@JsonProperty("base_power")
		@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Integer> basePower,
		@JsonProperty("accuracy")
		@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Double> accuracy,
		@JsonProperty("action_speed")
		@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Double> actionSpeed,
		@JsonProperty("bond_energy_cost")
		@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Integer> bondEnergyCost,
		@JsonProperty("stardust_tuning")
		@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Integer> stardustTuning,
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
			case 1 -> List.of(list.getFirst(), list.getFirst(), list.getFirst());
			case 3 -> List.copyOf(list);
			default -> throw new IllegalArgumentException("Size of " + name + " must be 1 or 3 but was " + list.size());
		};
	}

	public ResonanceMoveDTO {
		Objects.requireNonNull(moveCode, "Missing Move Code.");
		moveCode = StringUtils.upperSnakeCase(moveCode);
		Objects.requireNonNull(movePrototype, "Missing Move Prototype.");

		basePower = parseList(basePower, -1, "Base Power");
		accuracy = parseList(accuracy, 100.0, "Accuracy");
		actionSpeed = parseList(actionSpeed, 0.0, "Action Speed");
		bondEnergyCost = parseList(bondEnergyCost, 0, "Bond Energy Cost");
		stardustTuning = parseList(stardustTuning, 0, "Stardust Tuning");
		moveTarget = parseList(moveTarget, "SELF", "Move Target");
		moveTagList = parseList(moveTagList, List.of(), "Move Tags");
	}
	
	public ResonanceMove create(String starPartnerId) {
		ResonanceMove.Builder builder = new ResonanceMove.Builder(starPartnerId, moveId, moveCode, movePrototype);

		for (int moveMasteryRank = 0; moveMasteryRank <= MoveMasteryRank.MAX_MASTERY_RANK; ++moveMasteryRank) {
			MoveMasteryRank masteryRank = new MoveMasteryRank.Builder(moveMasteryRank)
					.setData(basePower.get(moveMasteryRank),
							accuracy.get(moveMasteryRank),
							actionSpeed.get(moveMasteryRank),
							bondEnergyCost.get(moveMasteryRank),
							stardustTuning.get(moveMasteryRank))
					.setTarget(moveTarget.get(moveMasteryRank))
					.setTags(moveTagList.get(moveMasteryRank))
					.build();

			builder.addMasteryRank(masteryRank);
		}
		
		return builder.build();
	}
}
