package com.gameplay.masterdata.data.master.dto.starpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.data.master.registry.move.battle.PokemonMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.battle.ResonanceMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.masterdata.template.move.Move;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveSlotDTO(
		@JsonProperty("class")
		String moveClass,
		@JsonProperty("code")
		String moveCode
) {
	public MoveSlotDTO {
		Objects.requireNonNull(moveClass, "Missing Move Class.");
		Objects.requireNonNull(moveCode, "Missing Move Code.");
	}

	public Move create() {
		return switch (moveClass) {
			case "POKEMON" -> PokemonMoveRegistry.getPokemonMoveByCode(moveCode);
			case "RESONANCE" -> ResonanceMoveRegistry.getResonanceMoveByCode(moveCode);
			case "ITEM" -> ItemMoveRegistry.getItemMoveByCode(moveCode);
			case "TRAINER" -> TrainerMoveRegistry.getTrainerMoveByCode(moveCode);
			default -> throw new IllegalArgumentException("Unknown Move Class : " + moveClass);
		};
	}
}
