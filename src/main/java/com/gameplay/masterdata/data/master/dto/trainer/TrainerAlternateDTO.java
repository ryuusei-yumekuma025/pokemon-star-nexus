package com.gameplay.masterdata.data.master.dto.trainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.data.master.dto.move.tactic.TrainerMoveDTO;
import com.gameplay.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.masterdata.template.move.tactic.trainer.TrainerMove;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.utils.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrainerAlternateDTO(
		@JsonProperty("id")
		int alternateId,
		@JsonProperty("code")
		String alternateCode,
		@JsonProperty("region")
		String region,
		@JsonProperty("trainer_classes")
		List<String> trainerClassList,
		@JsonProperty("moves")
		List<TrainerMoveDTO> trainerMoveDTOList,
		@JsonProperty("partners")
		List<String> partnerPokemon
) {
	public TrainerAlternateDTO {
		Objects.requireNonNull(alternateCode, "Missing Alternate Code.");
		alternateCode = StringUtils.upperSnakeCase(alternateCode);
		Objects.requireNonNull(region, "Missing Region.");
		trainerClassList = (trainerClassList != null) ? List.copyOf(trainerClassList) : List.of();
		trainerMoveDTOList = (trainerMoveDTOList != null) ? List.copyOf(trainerMoveDTOList) : List.of();
		partnerPokemon = (partnerPokemon != null) ? List.copyOf(partnerPokemon) : List.of();
	}

	public TrainerAlternate create(String trainerIdentityId) {
		TrainerAlternate.Builder builder = new TrainerAlternate.Builder(trainerIdentityId, alternateId, alternateCode)
				.setRegion(region)
				.setTrainerClasses(trainerClassList)
				.setPartnerPokemonList(partnerPokemon);

		for (TrainerMoveDTO trainerMoveDTO : trainerMoveDTOList) {
			TrainerMove trainerMove = trainerMoveDTO.create(trainerIdentityId + String.format("%03d", alternateId));

			if (trainerMove != null) {
				builder.addTrainerMove(trainerMove);
				TrainerMoveRegistry.register(trainerMove);
			}
		}

		return builder.build();
	}
}
